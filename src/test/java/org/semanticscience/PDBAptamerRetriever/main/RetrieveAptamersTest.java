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
package org.semanticscience.PDBAptamerRetriever.main;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticscience.PDBAptamerRetriever.bin.PDBAptamerRetriever;

/**
 * @author  Jose Cruz-Toledo
 *
 */
public class RetrieveAptamersTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/*@Test
	public void test() {
	  String [] args = new String [] {"-em", "X-RAY", "-mt", "DNA", "-lr", "-lf", "-pdbDir", "/home/jose/Documents/research/aptamers_inPDB/pdb_retriever/Nov2013/pdb/dna", "-pdbmlDir", "/home/jose/Documents/research/aptamers_inPDB/pdb_retriever/Nov2013/pdbml/dna"};
		RetrieveAptamers.main(args);
		
	}*/
	
	
	/*@Test
	public void test2() {
	  String [] args = new String [] {"-getall", "-pdbDir", "/tmp/pdb/dna/pdb","-em", "X-RAY", "-mt", "DNA","-pdbmlDir", "/tmp/pdb/dna/pdbml"};
		RetrieveAptamers.main(args);
	}*/
	
	@Test
	public void test3() {
	  String [] args = new String [] {"-getall", "-pdbDir", "/tmp/pdb/rna/pdb","-em", "X-RAY", "-mt", "RNA","-pdbmlDir", "/tmp/pdb/rna/pdbml"};
		RetrieveAptamers.main(args);
	}
}
