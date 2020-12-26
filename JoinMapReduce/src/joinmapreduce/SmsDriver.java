package joinmapreduce;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SmsDriver extends Configured implements Tool {
	public int run(String[] args) throws Exception {
		JobConf conf = new JobConf(getConf(), SmsDriver.class);
		conf.setJobName("SMS Reports");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		conf.setReducerClass(SmsReducer.class);
		MultipleInputs.addInputPath(conf, new Path(args[0]), TextInputFormat.class, UserFileMapper.class);
		MultipleInputs.addInputPath(conf, new Path(args[1]), TextInputFormat.class, DeliveryFileMapper.class);
		deleteDirectory(args[2], conf);
		FileOutputFormat.setOutputPath(conf, new Path(args[2]));
		JobClient.runJob(conf);
		return 0;
   }
	private static void deleteDirectory(String args, Configuration conf) throws IOException {
		Path p = new Path(args);
		FileSystem fs = FileSystem.get(conf);
		fs.exists(p);
		fs.delete(p, true);
	}
   public static void main(String[] args) throws Exception {
	   int res = ToolRunner.run(new Configuration(), new SmsDriver(), args);
	   System.exit(res);
   }
}