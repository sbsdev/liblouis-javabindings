package org.liblouis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Copyright (C) 2010 Swiss Library for the Blind, Visually Impaired and Print
 * Disabled
 * 
 * This file is part of liblouis-javabindings.
 * 
 * liblouis-javabindings is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

public class LouisTest {

	private final String de_g1 = "sbs.dis,sbs-de-core6.cti,sbs-de-accents.cti,sbs-special.cti,sbs-whitespace.mod,sbs-de-letsign.mod,sbs-numsign.mod,sbs-litdigit-upper.mod,sbs-de-g1-white.mod,sbs-de-core.mod,sbs-de-g1-core.mod,sbs-de-accents-ch.mod,sbs-special.mod";
	private final String de_g2 = "sbs.dis,sbs-de-core6.cti,sbs-de-accents.cti,sbs-special.cti,sbs-whitespace.mod,sbs-de-letsign.mod,sbs-numsign.mod,sbs-litdigit-upper.mod,sbs-de-core.mod,sbs-de-g2-core.mod,sbs-special.mod";

	private final String NBSP = "\u00A0";

	@Test
	public void testTranslation() {
		assertEquals("_w dom9,n",
				Louis.translate("en-us-g2.ctb", "world domination"));
	}

	@Test(expected = RuntimeException.class)
	public void testTranslationWrongTable() {
		Louis.translate("wrong_ctb_table_name", "world domination");
	}

	@Test
	public void testMultipleTables() {
		assertEquals(
				"if test fails, first check the list of liblouis tables (first string argument to Louis.translate)",
				"N*A BLAZON", Louis.translate(de_g2, "Nina Blazon"));
	}

	@Test
	public void testEmpty() {
		assertEquals("", Louis.translate(de_g2, ""));
	}

	@Test
	public void testSqueezeEnUs() {

		String a = "\n                       \t                                                                                          ";
		final String en_us = "en-us-g2.ctb";
		assertEquals("bla bla--bla",
				Louis.translate(en_us, "bla    bla\n" + a + "\t\t – bla"));
	}

	@Test
	public void testDashEnUs() {

		final String en_us = "en-us-g2.ctb";
		assertEquals("bla bla--bla",
				Louis.translate(en_us, "bla    bla  – bla"));
	}

	@Test
	public void testSqueezeDe() {

		String a = "\n                       \t                                                                                          ";
		assertEquals("BLA BLA'- BLA",
				Louis.translate(de_g2, "bla    bla\n" + a + "\t\t – bla"));
	}

	@Test
	public void testDe() {

		assertEquals("BLA BLA'- BLA", Louis.translate(de_g2, "bla bla – bla"));
	}

	@Test
	public void testNbsp() {

		assertEquals("BLA BLA", Louis.translate(de_g2, "BLA" + NBSP + "BLA"));
	}

	@Test
	public void testWhitespace() {
		assertEquals(
				" – b ",
				Louis.squeeze(" \t\n\r " + NBSP + "  – \t\n\r " + NBSP
						+ "   b \t\n\r " + NBSP + "  "));
	}
	
	@Test
	public void testHyphenationG1() {
		
		assertEquals("DAMPFt5IFFtB8t4ERtGEt]ELL", Louis.translate(de_g1, "Dampf­schiff­bü­cher­ge­stell"));
		assertEquals("WERTtINt]ANZtKAtPItT@N", Louis.translate(de_g1, "Wert­in­s­tanz­ka­pi­tän"));
		assertEquals("APtFELtKERNtREtAKtTOR", Louis.translate(de_g1, "Ap­fel­kern­re­ak­tor"));
		assertEquals("8BERt3FtRIGtK3T", Louis.translate(de_g1, "Über­eif­rig­keit"));
		assertEquals("OBStZ9tNItT@TStANtKLAtGEt5RIFT", Louis.translate(de_g1, "Obs­zö­ni­täts­an­kla­ge­schrift"));
		assertEquals("OBERtKELLtNERtJAtCKE", Louis.translate(de_g1, "Ober­kell­ner­ja­cke"));
		assertEquals("1tTOtMOtBILtHERt]ELtLEtRIN", Louis.translate(de_g1, "Au­to­mo­bil­her­stel­le­rin"));
		assertEquals("L0tBEStERtKL@tRUNGStVERtSU4", Louis.translate(de_g1, "Lie­bes­er­klä­rungs­ver­such"));
		assertEquals("POtLItTIKtWIStSENt5AFTStEXtPERtTE", Louis.translate(de_g1, "Po­li­tik­wis­sen­schafts­ex­per­te"));
		assertEquals("G3t^ENtPEtTERtM8TtZE", Louis.translate(de_g1, "Gei­ßen­pe­ter­müt­ze"));
		assertEquals("LItBYtENtAFtF@tREtPROtZESS", Louis.translate(de_g1, "Li­by­en­af­fä­re­pro­zess"));
		assertEquals("H\\tSERtBEtSETtZEtRINtNEN", Louis.translate(de_g1, "Häu­ser­be­set­ze­rin­nen"));
		assertEquals("KLINtKENtPUTtZERtSAtL@R", Louis.translate(de_g1, "Klin­ken­put­zer­sa­lär"));
		assertEquals("LI]IGtK3TStGELtTUNG", Louis.translate(de_g1, "Lis­tig­keits­gel­tung"));
		
	}
	
	@Test
	public void testHyphenationG2() {
		
		assertEquals("BAtDEtHOtSCtB/tD%", Louis.translate(de_g2, "Ba­de­ho­sen­bun­des"));
		assertEquals("B14tFOtTOS", Louis.translate(de_g2, "Bauch­fo­tos"));
		assertEquals("EHEtABtS#tTC", Louis.translate(de_g2, "Ehe­ab­sich­ten"));
		assertEquals("FAtBtRIKtBR+tD%", Louis.translate(de_g2, "Fa­b­rik­bran­des"));
		assertEquals("F7Nt;tZ0tHUStSK+tDAL", Louis.translate(de_g2, "Fern­be­zie­hungs­skan­dal"));
		assertEquals("GOURtMETtMCUtK)t(", Louis.translate(de_g2, "Gour­met­me­nu­kar­te"));
		assertEquals("HA5tH/tDEtH:StB+D", Louis.translate(de_g2, "Hasch­hun­de­hals­band"));
		assertEquals("H*tD7tXtP)t'COURSt&tB8HR", Louis.translate(de_g2, "Hin­der­nis­par­cours­ge­bühr"));
		assertEquals("*tDU]tR0tKLEtB7N", Louis.translate(de_g2, "In­dus­t­rie­kle­bern"));
		assertEquals("KATtZCtT'YPtMEtDItZ*", Louis.translate(de_g2, "Kat­zen­typ­me­di­zin"));
		assertEquals("KOtLUMtNCtB+DS", Louis.translate(de_g2, "Ko­lum­nen­bands"));
		assertEquals("5NURRtL1t(", Louis.translate(de_g2, "Schnurr­lau­te"));
		assertEquals("SONNtTAGtM?tGCtD@X7UC", Louis.translate(de_g2, "Sonn­tag­mor­gen­däm­me­run­gen"));
		assertEquals("TYEtFONtR/DtSPRU4S", Louis.translate(de_g2, "Te­le­fon­rund­spruchs"));
		assertEquals("TR2Et;t,3%", Louis.translate(de_g2, "Treue­be­wei­ses"));
		assertEquals(",?t,Bt,ZtBGCtDC", Louis.translate(de_g2, "vor­bei­zu­brin­gen­den"));
		assertEquals("W+tD7tWGt,37t]AHL", Louis.translate(de_g2, "Wan­der­weg­wei­ser­stahl"));
		assertEquals(",8tW<UStFILMtKAtM7A", Louis.translate(de_g2, "Über­wa­chungs­film­ka­me­ra"));
		
	}
}
