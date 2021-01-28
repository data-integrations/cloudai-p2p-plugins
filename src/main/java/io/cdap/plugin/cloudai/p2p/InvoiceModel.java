package io.cdap.plugin.cloudai.p2p;

import com.google.cloud.documentai.v1beta2.Document;
import com.google.cloud.documentai.v1beta2.Document.Entity;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.data.schema.Schema.Field;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class InvoiceModel {
  private static final Logger LOG = LoggerFactory.getLogger(InvoiceParserPlugin.class);

  private static final String INVOICE_ID = "invoice_id";
  private static final String INVOICE_DATE = "invoice_date";
  private static final String PURCHASE_ORDER = "purchase_order";
  private static final String TOTAL_AMOUNT = "total_amount";
  private static final String TOTAL_TAX_AMOUNT = "total_tax_amount";
  private static final String AMOUNT_DUE = "amount_due";
  private static final String DUE_DATE = "due_date";
  private static final String PAYMENT_TERMS = "payment_terms";
  private static final String SUPPLIER_NAME = "supplier_name";
  private static final String CURRENCY = "currency";
  private static final String RECEIVER_NAME = "receiver_name";
  private static final String RECEIVER_ADDRESS = "receiver_address";
  private static final String DELIVERY_DATE = "delivery_date";
  private static final String SUPPLIER_ADDRESS = "supplier_address";
  private static final String SUPPLIER_TAX_ID = "supplier_tax_id";
  private static final String CUSTOMER_TAX_ID = "customer_tax_id";
  private static final String CARRIER = "carrier";
  private static final String SHIP_TO_NAME = "ship_to_name";
  private static final String SHIP_TO_ADDRESS = "ship_to_address";
  private static final String SHIP_FROM_NAME = "ship_from_name";
  private static final String SHIP_FROM_ADDRESS = "ship_from_address";

  private static final String LINE_ITEMS = "line_items";
  private static final String LINE_ITEM = "line_item";
  private static final String LINE_ITEM_AMOUNT = "line_item/amount";
  private static final String LINE_ITEM_UNIT_PRICE = "line_item/unit_price";
  private static final String LINE_ITEM_QUANTITY = "line_item/quantity";
  private static final String LINE_ITEM_UNIT = "line_item/unit";
  private static final String LINE_ITEM_DESCRITION = "line_item/description";
  private static final String LINE_ITEM_PRODUCT_CODE = "line_item/product_code";

  private static final Set<String> LINE_ITEM_FIELDS = Sets.newHashSet(
      LINE_ITEM_AMOUNT,
      LINE_ITEM_UNIT_PRICE,
      LINE_ITEM_QUANTITY,
      LINE_ITEM_UNIT,
      LINE_ITEM_DESCRITION,
      LINE_ITEM_PRODUCT_CODE);

  public static final Schema INVOICE_LINE_ITEM_SCHEMA = getInvoiceLineItemSchema();
  public static final Schema INVOICE_SCHEMA = getInvoiceSchema();

  /**
   * Extracts invoice record from the DocAI Document response object.
   * @param doc Document object with invoice entities and entity relations.
   * @return Invoice record.
   */
  public StructuredRecord extractInvoiceRecord(Document doc) {
    List<Entity> invoiceEntities = doc.getEntitiesList().parallelStream().filter(new Predicate<Entity>() {
      @Override
      public boolean apply(@Nullable Entity entity) {
        if (!Strings.isNullOrEmpty(entity.getType())) {
          LOG.info("Invoice entity received - {}", entity);
          return true;
        } else {
          LOG.info("Non-invoice entity received - {}", entity);
          return false;
        }
      }
    }).collect(Collectors.toList());

    Map<String, String> entityRelations = new HashMap<>();
    doc.getEntityRelationsList().stream().forEach(
        relation -> {
          LOG.info("Entity relation received - {}", relation);
          entityRelations.put(relation.getObjectId(), relation.getSubjectId());
        });

    StructuredRecord.Builder invoice = StructuredRecord.builder(INVOICE_SCHEMA);
    Map<String, StructuredRecord.Builder> lineItems = Maps.newHashMap();

    for (Entity entity : invoiceEntities) {
      String type = entity.getType();
      boolean lineItem = LINE_ITEM_FIELDS.contains(type);
      Schema fieldSchema = lineItem ? INVOICE_LINE_ITEM_SCHEMA : INVOICE_SCHEMA;
      Schema.Field field = fieldSchema.getField(type);
      if (field == null) {
        LOG.error("Unexpected invoice entity - {}, matching invoice schema field type not found",
            entity);
        continue;
      }

      StructuredRecord.Builder record = invoice;
      if (lineItem) {
        String subjectId = entityRelations.get(entity.getMentionId());
        if (subjectId == null) {
          LOG.error("Unexpected invoice line item - {}, matching invoice relation not found",
              entity);
          continue;
        } else {
          record = lineItems.get(subjectId);
          if (record == null) {
            record = StructuredRecord.builder(INVOICE_LINE_ITEM_SCHEMA);
            lineItems.put(subjectId, record);
          }
        }
      }

      switch (field.getSchema().getType()) {
        case STRING:
          record.set(type, entity.getMentionText());
          break;
        case DOUBLE:
          try {
            record.set(type, Double.parseDouble(entity.getMentionText()));
          } catch (NumberFormatException e) {
            LOG.warn("Exception while parsing {} as a double. Error - {}", entity, e.getMessage());
          }
          break;
        default:
          break;
      }
    }

    if (!lineItems.isEmpty()) {
      StructuredRecord[] lineItemArr = new StructuredRecord[lineItems.size()];
      int index = 0;
      for (StructuredRecord.Builder lineItem : lineItems.values()) {
        lineItemArr[index++] = lineItem.build();
      }
      invoice.set(LINE_ITEMS, lineItemArr);
    }
    return invoice.build();
  }

  private static Schema getInvoiceLineItemSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of(LINE_ITEM_AMOUNT, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(LINE_ITEM_UNIT_PRICE, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(LINE_ITEM_QUANTITY, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(LINE_ITEM_UNIT, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(LINE_ITEM_DESCRITION, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(LINE_ITEM_PRODUCT_CODE, Schema.of(Schema.Type.STRING)));
    return Schema.recordOf(LINE_ITEM, fields);
  }

  private static Schema getInvoiceSchema() {
    List<Field> fields = Lists.newArrayList();
    fields.add(Field.of(INVOICE_ID, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(INVOICE_DATE, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(PURCHASE_ORDER, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(TOTAL_AMOUNT, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(TOTAL_TAX_AMOUNT, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(AMOUNT_DUE, Schema.of(Schema.Type.DOUBLE)));
    fields.add(Field.of(DUE_DATE, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(PAYMENT_TERMS, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SUPPLIER_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(CURRENCY, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(RECEIVER_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(RECEIVER_ADDRESS, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(DELIVERY_DATE, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SUPPLIER_ADDRESS, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SUPPLIER_TAX_ID, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(CUSTOMER_TAX_ID, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(CARRIER, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SHIP_TO_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SHIP_TO_ADDRESS, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SHIP_FROM_NAME, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(SHIP_FROM_ADDRESS, Schema.of(Schema.Type.STRING)));
    fields.add(Field.of(LINE_ITEMS, Schema.arrayOf(INVOICE_LINE_ITEM_SCHEMA)));
    return Schema.recordOf("invoiceSchema", fields);
  }
}
