# aws-s3-reader
Read files from amazon AWS S3 to local file system or write to S3 from local file system. 
Allow the AWS S3 to local File system sync on user event(on invoking the syncLocalDir).This will read all the newly created files from S3 bucket to local.
Implementation Requires few AWS S3 informations :
-'API_KEY'  which is the ASW S3 application key
-'API_SECRET' which is the ASW S3 application secret key
-'bucket-name' which is AWS S3 bucket-name
-'endpoint' An endpoint is a URL that is the entry point for this web service.To make any request to AWS S3 , Amazon Web Services products wants us to provide a regional endpoint for that.


Process Step :
-invoke syncLocalDir method to start the sync process
-provide endpoint to AWS services
-create S3Object
-Invoke the AmazonS3 credential check using AmazonS3 instance.
-doSync and writ it to local file system

This is a simple implementation of AWS S3 to local file system sync.
