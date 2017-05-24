<?php		
	$studentId = $_POST['studentId'];
	$school = $_POST['school'];
	$course = $_POST['course'];
	$semester = $_POST['semester'];
	$test = $_POST['test'];

	$dir = "data/" . $school . "/" . $course . "/" . $semester . "/" . $test . "/\$admin";
	$fileName =$_POST['fileName'];
	
	chdir($dir);

	$retContents = file_get_contents($fileName);

	echo $retContents; 
?>
