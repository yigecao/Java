# Makefile for cs 455 PA3 
#
# you shouldn't need to change anything in the file unless you
# want to submit additional files.
#
#-----------------------------------------------------------------------
#  Unix commands you can use when this file is in the current directory:
#
#     gmake getfiles
#        Copies and/or links data files and this Makefile to current directory.
#
#     gmake submit
#        Submits the assignment.
#
#-----------------------------------------------------------------------
#
# Variable definitions:

HOME = /home/scf-06/csci455
ASSGTS = $(HOME)/assgts
ASSGTDIR = $(ASSGTS)/pa3

#-----------------------------------------------------------------------

getfiles:
	-cp $(ASSGTDIR)/Makefile .
	-cp $(ASSGTDIR)/Maze.java .
	-cp $(ASSGTDIR)/MazeComponent.java .
	-cp $(ASSGTDIR)/MazeViewer.java .
	-cp $(ASSGTDIR)/MazeFrame.java .
	-cp $(ASSGTDIR)/MazeCoord.java .
	-cp $(ASSGTDIR)/README .
	-cp -r $(ASSGTDIR)/testfiles .

#-----------------------------------------------------------------------
# you will need to change the submit rule if you want to submit
# additional files.

submit:
	submit -user csci455 -tag pa3 README Maze.java MazeComponent.java MazeViewer.java


