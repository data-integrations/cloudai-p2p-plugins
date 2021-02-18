# File Copy to GCS Action Plugin

Description
-----------
This plugin copies files from an HDFS path to a Google Cloud Storage bucket.  A single files can be moved, or a
directory of files can be moved.

Credentials
-----------
If the plugin is run on a Google Cloud Dataproc cluster, the service account key does not need to be provided and can be
set to ‘auto-detect’. Credentials will be automatically read from the cluster environment.

If the plugin is not run on a Dataproc cluster, the path to a service account key must be provided. The service account
key can be found on the Dashboard in the Cloud Platform Console. Make sure the account key has permission to access
Google Cloud Storage.


Properties
----------
| Configuration | Required | Default | Description |
| :------------ | :------: | :------ | :---------- |
| **Source path** | **Y** | None | The full path of the file or directory that is to be copied. |
| **Project ID** | **N** | auto-detect | Google Cloud Project ID. |
| **Destination Bucket** | **Y** | None | The GCS bucket into which objects will be copied. |
| **Wildcard** | **N** | None | Wildcard regular expression to filter the files in the source directory. |
| **Number of parallel tasks** | **N** | 1 | Number of parallel tasks to use when executing the copy operation. |
| **Destination Bucket** | **Y** | None | The GCS bucket into which objects will be copied. |
| **Service Account File Path** | **N** | auto-detect | Path on the local file system of the service account key. |
| **Service Account  JSON** | **N** | None | Contents of the service account JSON file. |

Example
-----------

It is a good idea to add any usage notes for the plugin here to help the user get started.
