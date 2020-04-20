*************************
Computer Graphics - Ex.1

Anat Balzam - 205387954
Niv Shani - 311361661
*************************


Bonus function documentation:
*******************************
	Calculates the K optimal seams in the working image, and stores them in a list.
	Then, iterates through the result seams and sets each pixel's RGB to the given RGB value.
	The function returns a copy of the original working image, with the optimal seams colored.


General classes documentation:
*******************************

* ImagePixel
	An object representing a single pixel to be used in cost matrix and energy calculations.
	Contains the logic for calculating a pixel cost and tracing back a seam passing through this pixel.

* NeighborResult
	An object containing details about an ImagePixel optimal neighbor calculation result.

* Seam
	An object for representing a Seam. Contains the logic for calculating the optimal seam at the
	given state of the energy map, by calculating the current cost matrix and performing the dynamic
	programming procedures.
	
* SeamCoordinates
	An object for representing a pixel in a Seam.

* SeamCarvingResult
	An object containing the details of a seam removal or insertion operation -
	the updated image and the updated mask.

* SeamsReducer
	A class responsible for reducing the image width, by removing the given seams list.

* SeamsIncreaser
	A class responsible for increasing the image width, by inserting the given seams list.

* ObjectRemover
	A class responsible for removing the pixels contained in the given image mask. 
	Uses SeamCarver to manipulate the image dimensions.

