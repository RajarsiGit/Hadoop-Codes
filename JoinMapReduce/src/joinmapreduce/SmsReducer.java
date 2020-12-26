package joinmapreduce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class SmsReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
   private String customerName, deliveryReport;
   private static Map<String, String> DeliveryCodesMap = new HashMap<String, String>();
      
   public void configure(JobConf job) {
	   loadDeliveryStatusCodes();
   }
   public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	   while (values.hasNext()) {
		   String currValue = values.next().toString();
		   String valueSplitted[] = currValue.split("~");
		   if(valueSplitted[0].equals("CD")) {
			   customerName = valueSplitted[1].trim();
		   }
		   else if(valueSplitted[0].equals("DR")) {
			   deliveryReport = DeliveryCodesMap.get(valueSplitted[1].trim());
		   }
	   }
	   if(customerName != null && deliveryReport != null)
		   output.collect(new Text(customerName), new Text(deliveryReport));
	   else if(customerName == null)
		   output.collect(new Text("customerName"), new Text(deliveryReport));
	   else if(deliveryReport == null)
		   output.collect(new Text(customerName), new Text("deliveryReport"));
   }
   private void loadDeliveryStatusCodes() {
	   String strRead;
	   try {
		   BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Administrator\\eclipse-workspace\\JoinMapReduce\\DeliveryStatusCodes.txt"));
		   while ((strRead = reader.readLine() ) != null) {
			   String splitarray[] = strRead.split(",");
			   DeliveryCodesMap.put(splitarray[0].trim(), splitarray[1].trim());
		   }
		   reader.close();
	   }
	   catch (FileNotFoundException e) {
		   e.printStackTrace();
	   }
	   catch( IOException e ) {
		   e.printStackTrace();
	   }
   }
}