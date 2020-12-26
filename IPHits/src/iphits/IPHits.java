package iphits;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import iphits.IPHits;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IPHits {
	public static class IPHitsMapper extends Mapper<Object, Text, Text, IntWritable>{
		private Text ip = new Text();
		private final static IntWritable one = new IntWritable(1);
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		    StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
		    //String token = itr.nextToken();
		    while (itr.hasMoreTokens()) {
				String token = itr.nextToken();
				if(token.substring(0, token.indexOf(',')).equalsIgnoreCase("ip") == true)
					continue;
				ip.set(token.substring(0, token.indexOf(',')));
				context.write(ip, one);
		    }
		}
    }

    public static class IPHitsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		    int sum = 0;
		    for (IntWritable val : values) {
				sum += val.get();
		    }
		    result.set(sum);
		    context.write(key, result);
		}
    }
    
    private static void deleteDirectory(String args, Configuration conf) throws IOException {
		Path p = new Path(args);
		FileSystem fs = FileSystem.get(conf);
		fs.exists(p);
		fs.delete(p, true);
	}

    public static void main(String[] args) throws Exception {
	Configuration conf = new Configuration();
	Job job = new Job(conf, "Word Count Average");
	job.setJarByClass(IPHits.class);
	job.setMapperClass(IPHitsMapper.class);
	job.setReducerClass(IPHitsReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	deleteDirectory(args[1], conf);
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
