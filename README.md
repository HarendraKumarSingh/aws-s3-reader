# aws-s3-reader
Read files from amazon AWS S3 to local file system or write to S3 from local file system. 
Allow the AWS S3 to local File system sync on user event(on invoking the syncLocalDir).This will read all the newly created files from S3 bucket to local.


Implementation Requires few AWS S3 informations :

1'API_KEY'  which is the ASW S3 application key.

2.'API_SECRET' which is the ASW S3 application secret key.

3.'bucket-name' which is AWS S3 bucket-name.

4.'endpoint' An endpoint is a URL that is the entry point for this web service.To make any request to AWS S3 , Amazon Web   Services products wants us to provide a regional endpoint for that.


Process Step :

1.invoke syncLocalDir method to start the sync process.

2.provide endpoint to AWS services.

3.create S3Object.

4.Invoke the AmazonS3 credential check using AmazonS3 instance.

5.doSync and writ it to local file system.

This is a simple implementation of AWS S3 to local file system sync.

** Worked with below pom dependency :

	<dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk</artifactId>
            <version>1.8.2</version>
        </dependency>
