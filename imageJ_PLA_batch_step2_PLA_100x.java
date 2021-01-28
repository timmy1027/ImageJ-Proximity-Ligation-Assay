//Step 2


// Select the folder containing your images
dir1 = getDirectory("Choose Source Directory ");
list = getFileList(dir1);
// Select or create output folder
Output = getDirectory("Choose the output folder ");
//setBatchMode(true);
//delineate batch processing
for (k = 0; k<list.length; k++) { //list length is as many images as there are in the folder
showProgress(k+1, list.length);
open(dir1+list[k]); //open the first image (and then the next etc, after running)
imageName = getTitle(); //set “imageName” as the original name of each image
run("Split Channels");

//set "DAPI" as name of blue channel
selectWindow(imageName+" (blue)");
DAPI = getTitle();
run("16-bit");
selectWindow(imageName+" (red)");
//set "PLA" as name of red channel
PLA = getTitle();
run("16-bit");

selectImage(DAPI);
run("Subtract Background...", "rolling=250 sliding");
 run("Smooth");
 //count nucleus
 setAutoThreshold("Huang dark");
 setOption("BlackBackground", true);
 run("Convert to Mask");
 run("Watershed");

 //restrict PLA foci within nucleus

selectImage(PLA);
run("Subtract Background...", "rolling=125 sliding");
imageCalculator("AND create", PLA,DAPI);

//count PLA foci
selectImage("Result of "+imageName+" (red)");
run("Find Maxima...", "prominence=10 strict exclude output=[Maxima Within Tolerance]");
selectImage("Result of "+imageName+" (red) Maxima");
run("Analyze Particles...", "size=0-Infinity show=Nothing summarize");

//close all windows
run("Close All");
}
