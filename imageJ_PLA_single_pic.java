// https://vimeo.com/218520432
// Image Analysis with ImageJ/FIJI Workshop

//imagej manual: https://imagej.nih.gov/ij/docs/menus/analyze.html#manager



 selectWindow("hESC_ONS_SMAD123_PLA_6B4_NANOG_SMAD23_PLA_40X_F1.tif");
 a = getTitle();
 run("Split Channels");
 selectWindow(a+" (blue)");
 Blue = getTitle();
 run("16-bit");
 selectWindow(a+" (green)");
 close();
 selectWindow(a+" (red)");
 Red = getTitle();
 run("16-bit");

 selectImage(Blue);
 run("Subtract Background...", "rolling=125 sliding");
 run("Smooth");
 //count nucleus
 setAutoThreshold("Huang dark");
 setOption("BlackBackground", true);
 run("Convert to Mask");
 run("Watershed");
 run("Analyze Particles...", "size=0.30-Infinity show=Masks exclude summarize");
 //restrict foci within nucleus
 selectWindow("Mask of "+a+" (blue)");
 Blue_mask = getTitle();
 selectImage(Red);
 run("Subtract Background...", "rolling=125 sliding");
 imageCalculator("AND create", Red,Blue_mask);


selectWindow("Result of imageName (red)");
run("Find Maxima...", "prominence=10 exclude output=[Single Points]");
selectWindow("Result of imageName (red) Maxima");
run("Analyze Particles...", "size=0-Infinity show=Nothing summarize");

//close all windows
run("Close All");
}
