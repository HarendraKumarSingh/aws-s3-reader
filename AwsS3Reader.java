
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * This class allow the AWS S3 to local File system sync on user event(on invoking the syncLocalDir).This will read all the newly created files from S3 bucket to local.
 */
public class AwsS3Reader {

	/**
	 * API_KEY is the ASW S3 application key
	 */
	private final String API_KEY = "<access-key>”;
	/**
	 * API_SECRET is the ASW S3 application secret key
	 */
	private final String API_SECRET = "<secret-key>";
	/**
	 * amazonS3 is the in stance of AWS AmazonS3 with invokes the required credential check.
	 */
	private AmazonS3 amazonS3;
	/**
	*Local file system directory path where/from files from AWS s3 to local.
	*/
	private final String localDirPath = “<local-fs-directory-path>”;



	/**
	 * This method takes the aws-endpoint and the AWS S3 bucket-name to start the sync process.
	 * 
	 * @param endpoint  Amazon Web Services products allow us to select a regional endpoint to make any requests. An endpoint is a URL that is the entry point for this web service.
	 */
	
	public void awsS3Read(String endpoint) {
		AWSCredentials credentials = new BasicAWSCredentials(API_KEY,
				API_SECRET);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		amazonS3 = new AmazonS3Client(credentials, clientConfig);
		amazonS3.setEndpoint(endpoint);
	}

	/**
	 * @param bucketName  This the AWS S3 bucketname which contains files to be read	
	 * @throws IOException	Exception thrown when directory is empty, no file in it.
	 */
	public void doSync(String bucketName) throws IOException {
		
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(bucketName);
 		//Can also add only prefix filter to the files by : new ListObjectsRequest().withBucketName(bucketName).withPrefix(“prefix-name”);
		ObjectListing objectListing;

		File dir = new File(localDirPath);
		do {

			objectListing = amazonS3.listObjects(listObjectsRequest);
			for (S3ObjectSummary objectSummary : objectListing
					.getObjectSummaries()) {

				// System.out.println("KeyName : " + objectSummary.getKey());
				String keyName = "";
				keyName = objectSummary.getKey();
				
				S3Object s3Object = getObject(bucketName, keyName);
				
				InputStream reader = new BufferedInputStream(
						s3Object.getObjectContent());
				File file = new File(dir, keyName);

				OutputStream writer = new BufferedOutputStream(
						new FileOutputStream(file));

				int read = -1;

				while ((read = reader.read()) != -1) {
					writer.write(read);
				}

				writer.flush();
				writer.close();
				reader.close();
			}
			listObjectsRequest.setMarker(objectListing.getNextMarker());
		} while (objectListing.isTruncated());
	}

	/**
	 * @param bucketName  This the AWS S3 bucketname which has all the files to be read
	 * @param key	key is the file name from the aws bucket
	 * @return Returns AWS S3Object
	 */
	public S3Object getObject(String bucketName, String key) {
		S3Object s3Object = amazonS3.getObject(bucketName, key);
		return s3Object;
	}
	
	/**
	 * This method is the entry for AWS S3 to local directory sync process. 
	 */
	public void syncLocalDir(){
		
		awsS3Read("https://s3-ap-southeast-1.amazonaws.com");
		try {
			doSync(“<bucket-name>”);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.printStackTrace());		}
	}

	/**
	*main body optional
	*/
	public static void main(String[] args) {
		AwsS3Reader awsS3Reader = new AwsS3Reader();
		awsS3Reader.syncLocalDir();
	}
}
