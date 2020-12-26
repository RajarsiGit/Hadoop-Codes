package joinmapreduce;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class DeliveryFileMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>
{
    private String cellNumber, deliveryCode, fileTag = "DR~";
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException
    {
        String line = value.toString();
        String splitarray[] = line.split(",");
        cellNumber = splitarray[0].trim();
        deliveryCode = splitarray[1].trim();
        output.collect(new Text(cellNumber), new Text(fileTag + deliveryCode));
    }
}