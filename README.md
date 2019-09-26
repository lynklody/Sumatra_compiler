# A recursive descent parser / top-down parser program

This program is a compiler of a <b>virtual programming language Sumatra</b> written in <b>Java</b>. However, instead of compiling into lower level programming languages, <b>this compiler compiles the files written in Sumatra progamming language into Java files</b>. 

The basic phases of the compiler are <b>scanning</b>, <b>tokenizing</b>, <b>parsing</b>, <b>code generation</b> (happens at the same time with parsing)  and some <b>optimization</b> (for example, spaces were added to make the Java output file prettier). 

The first step is to get rid of all the comments (comments are in curly braces which can take up several consecutive lines of code) inside the Sumatra file during the scanning process. Then, each token in the Sumatra file is being tokenized into one of these categories: unsigned numbers, identifiers, keywords and special symbols, and illegal/unidentified element (Some of the test files I used contain illegal tokens. It was made this way on purpose). A typical Sumatra file contains program a header, some variable declarations, program body (statements) and a footer. The type of statements that this program can compile are <b>read statements, print statements, if statements, while statements, do statements, and assignment statements</b>. Each statement may contain many expression that are made of many subcomponents like variables, arithmetic operators, relational operators and conditions.

<b>Usage:</b><br/>
There are two sample input files (bubblesort.sum and straddle.sum) in folder 'sample_IO'.
When running the Driver file, you will be prompted "Enter the file name without extension:".
After cloning the current file system, all you need to do is to enter "sample_IO/bubblesort" or "sample_IO/straddle" to try out the two examples.
