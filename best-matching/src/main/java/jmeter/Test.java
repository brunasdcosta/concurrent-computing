package jmeter;

import java.io.Serializable;
import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import serial.BestMatching;
//import mutex.BestMatching;
//import atomic.BestMatching;

public class Test extends AbstractJavaSamplerClient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		String filePath = context.getParameter("filePath");
		String searchWord = context.getParameter("searchWord");
		SampleResult result = new SampleResult();
		result.sampleStart();
		result.setSampleLabel("Test Sample");
		BestMatching bm = new BestMatching(filePath, searchWord);
		bm.runAlgorithm();
		List<String> resultList = bm.getLevenshtein().getLowestsLevenshteinDistance();
		if (resultList != null && resultList.size() == 2 && resultList.contains("a2cmpitn97yn")
				&& resultList.contains("a2cmp5nt73gy")) {
			result.sampleEnd();
			result.setResponseCode("200");
			result.setResponseMessage("OK");
			result.setSuccessful(true);
		} else {
			result.sampleEnd();
			result.setResponseCode("500");
			result.setResponseMessage("NOK");
			result.setSuccessful(false);
		}
		return result;
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments defaultParameters = new Arguments();
		defaultParameters.addArgument("filePath", "/home/bruna/workspace/concurrent-computing/dataset.txt");
		defaultParameters.addArgument("searchWord", "a2cmpXXXXXXX");
		return defaultParameters;
	}

}