import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.lightsail.model.StopInstanceRequest;

public class EC2Commands {
	
	static AmazonEC2 amazonEC2client;
	static String keyName = "20170301";
	static String sgName = "launch-wizard-2";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		amazonEC2client = AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();
		createInstance();
		//stopInstance();
		//terminateInstance();
	}
	
	static void createInstance(){
		RunInstancesRequest run = new RunInstancesRequest();
		run.withImageId("ami-9db937fd").withInstanceType("t2.micro").withMinCount(1).withMaxCount(1)
			.withKeyName(keyName).withSecurityGroups(sgName);
		RunInstancesResult result = amazonEC2client.runInstances(run);
		System.out.println("Instance description: "+result.toString());
	}
	
	static void stopInstance() {
		StopInstancesRequest stop = new StopInstancesRequest();
		stop.withInstanceIds("i-0d36ae89a13ea8dcb");
		StopInstancesResult result = amazonEC2client.stopInstances(stop);
		System.out.println("Stop Result: "+result.toString());
	}
	
	static void terminateInstance() {
		TerminateInstancesRequest terminate = new TerminateInstancesRequest();
		terminate.withInstanceIds("i-0d36ae89a13ea8dcb");
		TerminateInstancesResult result = amazonEC2client.terminateInstances(terminate);
		System.out.println("Terminate Result: "+result.toString());
	}
	
	static void createInstance(Boolean withUserData) {
		String userData = "#!/bin/bash\n mkdir jorge";
		String formattedStr = Base64.encodeBase64String(userData.getBytes());
		RunInstancesRequest run = new RunInstancesRequest();
		run.withImageId("ami-f173cc91").withInstanceType("t2.micro").withMinCount(1).withMaxCount(1)
			.withKeyName(keyName).withSecurityGroups(sgName).withUserData(formattedStr);
		RunInstancesResult result = amazonEC2client.runInstances(run);
		System.out.println("Instance description: "+result.toString());
	}
	
	static void listInstances() {
		DescribeInstancesResult result = amazonEC2client.describeInstances();
		List<Reservation> reservations = result.getReservations();
		for (Reservation res : reservations) {
			List<Instance> instances = res.getInstances();
			for (Instance i : instances) {
				System.out.println("Instance description: "+i.toString());
			}
		}
	}

}
