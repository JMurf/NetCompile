<?php
	require('Compile.php');
	$root = "..\\..\\Users\\jonat\\Documents\\testTaker";
 	$root = "";	
	
	$school = $_POST["school"];//StrToLower($_POST["school"]);
	$classID = $_POST["classID"];//StrToLower($_POST["classID"]);
	$semester = $_POST["semester"];//StrToLower($_POST["semester"]);
	$testDesc = $_POST["testDesc"];//StrToLower($_POST["testDesc"]);
	$studentID = $_POST["studentID"];//StrToLower($_POST["studentID"]);
	$fileName= $_POST["fileName"];
	$content= /*utf8_decode(*/$_POST["content"]/*)*/; 
	$time = date("Y-m-d_h_i_s_a");
		
	if( $classID != null ) 
	{
		$dir = "data/".$school."/".$classID."/".$semester."/".$testDesc."/".$fileName."/".$studentID."/".$time;
		$adminDir = "C:/xampp/htdocs/danielOluwadare.com/data/".$school."/".$classID."/".$semester."/".$testDesc. "/\$admin";
	}
	else
	{
		exit( "Missing or invalid post data!" );		
	}

	$srcFile = $fileName.".java";
	$classFile = $fileName;

        //set up directory
	if ( !is_dir($dir) ) {
		mkdir($dir, 0777, true);
	}
	chdir($dir);
	
	$file=fopen($srcFile,"w");	
	fwrite($file, $content);
	fclose($file);

	echo "Admin page = " . $adminDir . "\n";
	compileClassFile($fileName, $dir, $adminDir);
	
    //exec("C:/ProgramFiles/Java/jdk1.8.0_121/bin/javac " . $srcFile . " 2> temp2.txt", $array);
    //echo "array = " . count($array) . "</br>"; 

    //exec("java " . $classFile, $outputArray); 
    //echo "Output array = " . count($outputArray) . "</br>";

    //$outFile = fopen($fileName . ".txt", "w");
    //for( $i=0; $i<count($outputArray); $i++ )
    //{
    //	fwrite($outFile, "Ouput line #" . $i . ": " . $outputArray[$i] . "\n");
    //} 

    //fclose($outFile);
    //echo $classFile . ".txt" . "</br>";
?>