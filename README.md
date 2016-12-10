SIEParser
======
The SIEParser is a port to Java of the .NET jsisie parser

Original .NET code is at https://github.com/idstam/jsisie


A Java parser for SIE files that can read files of version 1 to 4 (including 4i)

##Read a SIE file

To read a file create an instance of SieDocument and call ReadDocument.

There are some boolean properties in SieDocument that changes how the parsing works:

+ IgnoreMissingOMFATTNING: If true the parser will not flag a missing #OMFATTN as an error.
+ IgnoreBTRANS: If true #BTRANS (removed voucher rows) will be ignored.
+ IgnoreRTRANS: If true #RTRANS (added voucher rows) will be ignored.
+ StreamValues: If true don't store values internally. The user has to use the Callback class to get the values. Usefull for large files.
+ ThrowErrors: If false then cache all Exceptions in SieDocument.ValidationExceptions

See the alipsa.sieparser.TestSieDocument junit test for details of a sample usage

**Not all features are implemented yet:**

+ #UNDERDIM: There are no instances of this in the published example files.

##Write a SIE file

To write a file create an instance of SieDocumentWriter and call WriteDocument

##Compare SIE files

To compare SIE files call the static method Compare on SieDocumentComparer. It will return a List&lt;string&gt; with differences between the files.  
 

Even if you use this parser you should get familiar with the file specification.
I have not made any extensive efforts to validate the resulting data against the files so please do your validation and tell me if you find any errors.


Follow [this link](http://www.sie.se/?page_id=20) for swedish versions and [this link](http://www.sie.se/?page_id=250) for english versions

##Change log
Version 1: Focused on a working port of the jsisie parser to java. 

##Roadmap
Version 1.x: 
- Make 4s to work properly
- Implement #UNDERDIM
- Add support for SIE XML
