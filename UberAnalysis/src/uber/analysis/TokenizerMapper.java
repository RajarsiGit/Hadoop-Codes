package uber.analysis;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM/dd/yyyy");
	String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	private Text basement = new Text();
	Date date = null;
	private int trips;
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		String[] splits = line.split(",");
		basement.set(splits[0]);
		try {
			date = format.parse(splits[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trips = new Integer(splits[3]);
		@SuppressWarnings("deprecation")
		String keys = basement.toString() + " " + days[date.getDay()];
		context.write(new Text(keys), new IntWritable(trips));
	}
}