package wordcountjob;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class WCJMapper2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
	private final static IntWritable one = new IntWritable(1);
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
		StringTokenizer itr = new StringTokenizer(value.toString());
    	while (itr.hasMoreTokens()) {
    		output.collect(new Text(itr.nextToken()), one);
    	}
	}
}
