/*
    Copyright 2005 VTT Biotechnology

    This file is part of MZmine.

    MZmine is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    MZmine is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with MZmine; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package net.sf.mzmine.rawdatavisualizers;
import net.sf.mzmine.alignmentresultmethods.*;
import net.sf.mzmine.alignmentresultvisualizers.*;
import net.sf.mzmine.datastructures.*;
import net.sf.mzmine.distributionframework.*;
import net.sf.mzmine.miscellaneous.*;
import net.sf.mzmine.peaklistmethods.*;
import net.sf.mzmine.rawdatamethods.*;
import net.sf.mzmine.rawdatavisualizers.*;
import net.sf.mzmine.userinterface.*;


// Java packages
import java.awt.*;

import javax.swing.*;
import java.util.*;


import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;

import java.awt.geom.Point2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


import java.awt.color.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;

import javax.print.attribute.HashPrintRequestAttributeSet;

import java.text.DecimalFormat;

import java.awt.datatransfer.Clipboard;



public class RawDataVisualizerTwoDCalc {

	private RawDataVisualizerRefreshRequest refreshRequest;

	// Size of the bitmap (in pixels)
	private int imgHeight;
	private int imgWidth;

	// Bitmap intensities & minimum and maximum intensity
	private double[][] dataImg;
	private double dataImgMax;
	private double dataImgMin;

	// Number of scans assigned to this pixel column so far
	int scanCountForX;

	// Current pixel column
	int imgXInd;


	private Vector interpScanNums;
	private Vector interpMZVals;
	private Vector dataPoints;


	public void refreshInitialize(RawDataVisualizerRefreshRequest _refreshRequest) {

		refreshRequest = _refreshRequest;

		// If visualizer needs raw data, then it means we are going to redraw the bitmap image
		if (refreshRequest.twodNeedsRawData) {
			// Allocate matrix for the bitmap
			dataImg = new double[refreshRequest.twodXResolution][refreshRequest.twodYResolution];

			// Reset maximum and minimum intensity
			dataImgMax = Double.MIN_VALUE;
			dataImgMin = Double.MAX_VALUE;

			// Reset bitmap column counters
			scanCountForX = 0;
			imgXInd = 0;
		}

	}


	public void refreshHaveOneScan(Scan s) {

		scanCountForX++;

		// Bin intensities of a spectrum
		double[] tmpInts;

		if (refreshRequest.dataType == RawDataVisualizerRefreshRequest.MODE_CONTINUOUS) {
			tmpInts = s.getBinnedIntensities(refreshRequest.twodStartMZ, refreshRequest.twodStopMZ, refreshRequest.twodYResolution, true);
		} else {
			tmpInts = s.getBinnedIntensities(refreshRequest.twodStartMZ, refreshRequest.twodStopMZ, refreshRequest.twodYResolution, false);
		}

		// Use bin intensity only if it is current maximum for an element in the bitmap
		for (int imgYInd=0; imgYInd<refreshRequest.twodYResolution; imgYInd++) {
			double tmpInt = tmpInts[tmpInts.length-imgYInd-1];

			if (tmpInt>=dataImg[imgXInd][imgYInd]) {
				dataImg[imgXInd][imgYInd] = tmpInt;
			}
		}

		// Is It time to move to next pixel column?
		if (scanCountForX==refreshRequest.twodScansPerX) {
			scanCountForX = 0;
			imgXInd++;
		}

	}


	public void refreshFinalize() {

		// If we have just redrawn the bitmap picture
		if (refreshRequest.twodNeedsRawData) {

			// Search for maximum and minimum intensity of the bitmap
			for (int imgXInd=0; imgXInd<refreshRequest.twodXResolution; imgXInd++) {
				for (int imgYInd=0; imgYInd<refreshRequest.twodYResolution; imgYInd++) {
					double tmpInt = dataImg[imgXInd][imgYInd];
					if (tmpInt >= dataImgMax) { dataImgMax = tmpInt; }
					if (tmpInt <= dataImgMin) { dataImgMin = tmpInt; }
				}
			}
		}
	}

	public double[][] getMatrix() {
		return dataImg;
	}

	public double getMaxIntensity() {
		return dataImgMax;
	}

	public double getMinIntensity() {
		return dataImgMin;
	}

}



