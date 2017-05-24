<?php	
	function compileClassFile($fileName, $dir, $adminDir){
		$outp = array();
    	exec("C:/Progra~1/Java/jdk1.8.0_121/bin/javac " . $fileName . ".java  2>&1 ", $outp);

		if( count($outp) > 0 ) {
			echo json_encode($outp);
		}
		else {
			echo $dir . " " . $fileName . "\n";
			exec( "java -classpath C:/xampp/htdocs/danielOluwadare.com SystemInOutRouter " . $dir . " " . $fileName . " " . $adminDir . " 2>&1 ", $outp );				
			echo "Wrapping it up!\n";
			echo json_encode($outp);
		}
	}
?>