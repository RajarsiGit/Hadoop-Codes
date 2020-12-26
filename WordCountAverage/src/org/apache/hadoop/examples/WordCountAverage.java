package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountAverage {
	
    public static class WCAMapper extends Mapper<Object, Text, Text, IntWritable>{
		private Text firstCharacter = new Text();
		private final static IntWritable length = new IntWritable(1);
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		    StringTokenizer itr = new StringTokenizer(value.toString());
		    while (itr.hasMoreTokens()) {
				String token = itr.nextToken();
				firstCharacter.set(token.substring(0,1));
				length.set(token.length());
				context.write(firstCharacter, length);
		    }
		}
    }

    public static class WCAReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {
		private DoubleWritable result = new DoubleWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		    double sum = 0;
		    int total = 0;
		    for (IntWritable val : values) {
				total ++;
				sum += val.get();
		    }
		    double average = sum / total;
		    result.set(average);
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
	job.setJarByClass(WordCountAverage.class);
	job.setMapperClass(WCAMapper.class);
	job.setReducerClass(WCAReducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	deleteDirectory(args[1], conf);
	System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}