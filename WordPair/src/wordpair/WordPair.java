package wordpair;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordPair {
	public static class WPMapper extends Mapper<Object, Text, Text, IntWritable> {
	    private final static IntWritable one = new IntWritable(1);
	    private Text word = new Text();
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    	StringTokenizer itr = new StringTokenizer(value.toString(), " ");
	    	if(itr.hasMoreTokens()) {
		    	String str1 = itr.nextToken(), str2 = "";
		    	while (itr.hasMoreTokens()) {
		    		str2 = itr.nextToken();
	    			word.set(str1.trim() + " " + str2.trim());
		    		context.write(word, one);
		    		str1 = str2;
		    	}
	    	}
	    }
	}

  	public static class WPReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
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
  		
	    Job job = Job.getInstance(conf, "Total words next to each other");
	    job.setJarByClass(WordPair.class);
	    job.setMapperClass(WPMapper.class);
	    job.setCombinerClass(WPReducer.class);
	    job.setReducerClass(WPReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    deleteDirectory(args[1], conf);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
  	}
}