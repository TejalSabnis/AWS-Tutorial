import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;


public class S3Commands {
	
	static AmazonS3 s3client;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		s3client = new AmazonS3Client();
	}
	
	static void createBucket() {
		String bucketName = "jc-aws-tutorial";
		Bucket buck = s3client.createBucket(bucketName);
		System.out.println("Creating bucket: "+buck.toString());
	}
	
	static void listBucket() {
		List<Bucket> buckets = s3client.listBuckets();
		System.out.println("Amazon buckets: ");
		for(Bucket b : buckets) {
			System.out.println(b.getName());
		}
	}
	
	static void putObject() {
		String name = "", key = "";
		s3client.putObject(name, key, new File(""));
	}
}
