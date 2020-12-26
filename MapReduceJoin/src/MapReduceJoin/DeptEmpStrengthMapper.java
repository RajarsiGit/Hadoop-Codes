
package MapReduceJoin;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class DeptEmpStrengthMapper extends MapReduceBase implements Mapper<LongWritable, Text, TextPair, Text> {

	@Override
	public void map(LongWritable key, Text value, OutputCollector<TextPair, Text> output, Reporter reporter) 
			throws IOException 
	{	
	
		String valueString = value.toString();
		String[] SingleNodeData = valueString.split("\t");
		output.collect(new TextPair(SingleNodeData[0], "1"), new Text(SingleNodeData[1]));
	}
}

