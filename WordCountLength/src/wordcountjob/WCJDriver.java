package wordcountjob;

import java.io.IOException;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.*;

public class WCJDriver extends Configured implements Tool {
	public int run(String[] args) {

		JobConf job_conf = new JobConf(WCJDriver.class);
		job_conf.setJobName("Total Length");
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(IntWritable.class);
		job_conf.setMapperClass(wordcountjob.WCJMapper1.class);
		job_conf.setReducerClass(wordcountjob.WCJReducer1.class);
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));
		
		JobConf job_conf1 = new JobConf(WCJDriver.class);
		job_conf1.setJobName("Word Count");
		job_conf1.setOutputKeyClass(Text.class);
		job_conf1.setOutputValueClass(IntWritable.class);
		job_conf1.setMapperClass(wordcountjob.WCJMapper2.class);
		job_conf1.setReducerClass(wordcountjob.WCJReducer2.class);
		job_conf1.setInputFormat(TextInputFormat.class);
		job_conf1.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(job_conf1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job_conf1, new Path(args[2]));
		
		int result = -1;
		try {
			deleteDirectory(args[1], job_conf);
			Job job1 = Job.getInstance(job_conf);
			result = job1.waitForCompletion(true) ? 0 : 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\nFirst Job Finished=============\n");
		if(result == 0) {
			try {
				deleteDirectory(args[2], job_conf1);
				Job job2 = Job.getInstance(job_conf1);
				result = job2.waitForCompletion(true) ? 0 : 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("\nSecond Job Finished=============\n");
		}
		return result;
	}
	
	private static void deleteDirectory(String args, Configuration conf) throws IOException {
		Path p = new Path(args);
		FileSystem fs = FileSystem.get(conf);
		fs.exists(p);
		fs.delete(p, true);
	}
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new WCJDriver(), args);
		System.exit(res);
    }
}
