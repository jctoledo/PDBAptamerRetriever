/**
 * Copyright (c) 2013  Jose Cruz-Toledo
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.semanticscience.PDBAptamerRetriever.bin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * @author Jose Cruz-Toledo
 * 
 */
public class PairwiseAlignmentParser {
	/**
	 * Parses the input sequence alignment file and prints a csv file
	 * 
	 * @param aSeqAlignment
	 *            a pairwise sequence alignemnt file as generated by Jalview 2.8
	 * @param outputCSV
	 *            where to save the CSV file
	 */
	public static void printCSVFile(File aSeqAlignment, File outputCSV) {
		// make sure that outputCSV is empty
		outputCSV = eraseFile(outputCSV);
		try {
			List<String> contents = FileUtils.readLines(aSeqAlignment);
			/**
			 * A map where the key is a pdb-pdb pair (no chains) The value is a
			 * map where there are 3 keys: avg-score, avg-ident, count scores: a
			 * list of all the scores seen for that pair idents: a list of all
			 * the percent identities seen for that pair:
			 */
			Map<String, Map<String, List<Double>>> cleanPairs = new HashMap<String, Map<String, List<Double>>>();
			String buf = "";
			for (String aLine : contents) {
				Pattern p = Pattern.compile(".*\\d+\\.\\d+");
				Matcher m = p.matcher(aLine);
				if (m.matches()) {
					buf += aLine + "\n";
					String id = retrievePDBIdentifiersFromAlignment(buf.trim());
					String score = retrieveScoreFromAlignment(buf.trim());
					String percentIdentity = retrievePercentIdentityFromAlignment(buf
							.trim());

					// check if the pair is comparing itself
					String[] pairIds = id.split("-");
					if (!pairIds[0].equals(pairIds[1])) {
						if (score != null && percentIdentity != null) {
							// compute reverse pair
							String rid = pairIds[1] + "-" + pairIds[0];
							// now add the pair to cleanPAirs
							if (!cleanPairs.containsKey(id)
									&& !cleanPairs.containsKey(rid)) {
								Map<String, List<Double>> map = new HashMap<String, List<Double>>();
								List<Double> sl = new ArrayList<Double>();
								sl.add(Double.parseDouble(score));
								map.put("score", sl);
								List<Double> il = new ArrayList<Double>();
								il.add(Double.parseDouble(percentIdentity));
								map.put("idents", il);
								cleanPairs.put(id, map);
							} else {
								Map<String, List<Double>> map = new HashMap<String, List<Double>>();
								Double new_score = Double.parseDouble(score);
								List<Double> l = cleanPairs.get(id)
										.get("score");
								l.add(new_score);
								map.put("score", l);
								Double new_ident = Double
										.parseDouble(percentIdentity);
								List<Double> l2 = cleanPairs.get(id).get(
										"idents");
								l2.add(new_ident);
								map.put("idents", l2);
								cleanPairs.put(id, map);
							}
						}
					}
					buf = "";
				} else {
					buf += aLine + "\n";
				}
			}
			// now average the values for score and idents
			Map<String, Map<String, Double>> out = new HashMap<String, Map<String, Double>>();
			for (String pId : cleanPairs.keySet()) {
				Map<String, List<Double>> lm = cleanPairs.get(pId);
				List<Double> scores = lm.get("score");
				double scores_avg = average(scores);
				List<Double> idents = lm.get("idents");
				double idents_avg = average(idents);
				Map<String, Double> q = new HashMap<String, Double>();
				q.put("score_avg", scores_avg);
				q.put("idents_avg", idents_avg);
				out.put(pId, q);
			}

			// now that we have what we need we print the out map onto a csv
			// file
			String csvOut = "PAIR,PERCENT-IDENTITY,SCORE\n";
			for (String pId : out.keySet()) {
				Map<String, Double> sd = out.get(pId);
				csvOut += pId + "," + sd.get("idents_avg") + ","
						+ sd.get("score_avg") + "\n";
			}
			FileUtils.writeStringToFile(outputCSV, csvOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String retrieveScoreFromAlignment(String anAlign) {
		String[] s = anAlign.split("\n");
		if (s.length > 0) {
			String scoreRaw = s[0];
			String[] q = scoreRaw.split("=");
			if (q.length > 0) {
				String score = q[1].trim();
				return score;
			}
		}
		return null;
	}

	private static double average(List<Double> list) {
		// 'average' is undefined if there are no elements in the list.
		if (list == null || list.isEmpty())
			return 0.0;
		// Calculate the summation of the elements in the list
		long sum = 0;
		int n = list.size();
		// Iterating manually is faster than using an enhanced for loop.
		for (int i = 0; i < n; i++)
			sum += list.get(i);
		// We don't want to perform an integer division, so the cast is
		// mandatory.
		return ((double) sum) / n;
	}

	private static String retrievePDBIdentifiersFromAlignment(String anAlign) {
		String[] s = anAlign.split("\n");
		if (s.length > 0) {
			String od = s[5];
			String id = s[7];
			String[] r = od.split("\\|");
			String[] q = id.split("\\|");
			if (q.length > 0 && r.length > 0) {
				// remove the chains
				String[] ra = r[0].split(":");
				String[] qa = q[0].split(":");
				if (ra[0].length() > 0 && qa[0].length() > 0) {
					String rm = ra[0] + "-" + qa[0];
					return rm;
				}
			}
		}
		return null;
	}

	private static String retrievePercentIdentityFromAlignment(String anAlign) {
		String[] s = anAlign.split("\n");
		if (s.length > 0) {
			String id = s[9];
			String[] q = id.split("=");
			if (q.length > 1) {
				String score = q[1].trim();
				return score;
			}
		}
		return null;
	}

	private static File eraseFile(File aF) {
		FileOutputStream erasor;
		try {
			erasor = new FileOutputStream(aF);
			erasor.write((new String()).getBytes());
			erasor.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aF;
	}
}