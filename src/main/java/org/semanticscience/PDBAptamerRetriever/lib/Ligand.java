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
package org.semanticscience.PDBAptamerRetriever.lib;

import java.util.ArrayList;
import java.util.List;
//TODO: use REST to get the list of pdbids for a given ligand
/**
 * @author Jose Cruz-Toledo
 * 
 */
public class Ligand {
	private List<String> pdbIds = null;
	private String chemicalId = null;
	private String type = null;
	private Double molecularWeight = 0.0;
	private String chemicalName = null;
	private String formula = null;
	private String inchiKey = null;
	private String inchi = null;
	private String chebiId = null;
	private String smiles = null;
	private boolean isION = false;
	

	
	/**
	 * @param chemicalId
	 *            the chemical identifier provided by ligandexpo
	 * @param type
	 *            the mmCIF type
	 * @param molecularWeight
	 *            molecular weigth
	 * @param chemicalName
	 *            a chemical name
	 * @param formula
	 *            the chemical formula
	 * @param inchiKey
	 *            inchikey string
	 * @param inchi
	 *            the inchi string
	 * @param smiles
	 *            a smiles string
	 */
	public Ligand(String chemicalId, String type,
			Double molecularWeight, String chemicalName, String formula,
			String inchiKey, String inchi, String smiles, String aChebiId, boolean anIsION) {
		this(chemicalId, type, molecularWeight, chemicalName, formula, inchiKey, inchi, smiles, aChebiId);
		this.isION = anIsION;
	}
	
	public Ligand(String chemicalId, String type,
			Double molecularWeight, String chemicalName, String formula,
			String inchiKey, String inchi, String smiles, String chebiId){
		this.chemicalId = chemicalId;
		this.type = type;
		this.chebiId = chebiId;
		this.molecularWeight = molecularWeight;
		this.chemicalName = chemicalName;
		this.formula = formula;
		this.inchiKey = inchiKey;
		this.inchi = inchi;
		this.smiles = smiles;
		this.pdbIds = new ArrayList<String>();
	}
	
	public String getCSVHeader(){
		String b ="";
		b += "PDBID\tCHEMICAL ID\tCHEMICAL NAME\tTYPE\tMW\tFORMULA\tINCHI\tINCHIKEY\tSMILES\n";
		return b;
	}
	public String getCSVLine() {
		String b = "";
		b += getPdbIds() + "\t" + getChemicalId() + "\t"
				+ getChemicalName().replaceAll(",", "") + "\t" + getType()
				+"\t"+getMolecularWeight()+"\t"+getFormula()+"\t"+getSmiles()+"\n";
		return b;
	}
	/**
	 * Retrieve a list of all known pdbIds where this ligand is found
	 * @return a list of pdbids
	 */
	public List<String> getPdbIds(){
		return this.pdbIds;
	}

	/**
	 * @return the chemicalId
	 */
	public String getChemicalId() {
		return chemicalId;
	}

	/**
	 * Adds a PDB id to the list of PDB ids for this ligand. 
	 * Returns true if added to the list. False if it was already in the list
	 * @param aPdbId a pdb id that you wish to add
	 * @return true if added to the list. False if it was already in the list
	 */
	public boolean addPdbId(String aPdbId){
		//check if the PDB ID is in the list
		if(this.getPdbIds().contains(aPdbId)){
			return false;
		}else{
			pdbIds.add(aPdbId);
			return true;
		}
		
	}
	public void setIsION(boolean anIsION){
		this.isION = anIsION;
	}
	public boolean isION(){
		return isION;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * @return the molecularWeight
	 */
	public Double getMolecularWeight() {
		return molecularWeight;
	}

	

	/**
	 * @return the chemicalName
	 */
	public String getChemicalName() {
		return chemicalName;
	}


	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}


	/**
	 * @return the inchiKey
	 */
	public String getInchiKey() {
		return inchiKey;
	}

	

	/**
	 * @return the inchi
	 */
	public String getInchi() {
		return inchi;
	}

	/**
	 * @return the smiles
	 */
	public String getSmiles() {
		return smiles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chemicalId == null) ? 0 : chemicalId.hashCode());
		result = prime * result
				+ ((chemicalName == null) ? 0 : chemicalName.hashCode());
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result
				+ ((molecularWeight == null) ? 0 : molecularWeight.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ligand other = (Ligand) obj;
		if (chemicalId == null) {
			if (other.chemicalId != null)
				return false;
		} else if (!chemicalId.equals(other.chemicalId))
			return false;
		if (chemicalName == null) {
			if (other.chemicalName != null)
				return false;
		} else if (!chemicalName.equals(other.chemicalName))
			return false;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (molecularWeight == null) {
			if (other.molecularWeight != null)
				return false;
		} else if (!molecularWeight.equals(other.molecularWeight))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ligand [pdbIds=" + pdbIds + ", chemicalId=" + chemicalId
				+ ", type=" + type + ", molecularWeight=" + molecularWeight
				+ ", chemicalName=" + chemicalName + ", formula=" + formula
				+ ", inchiKey=" + inchiKey + ", inchi=" + inchi + ", chebiId="
				+ chebiId + ", smiles=" + smiles + ", isION=" + isION + "]";
	}



}
