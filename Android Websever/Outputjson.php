<?php

	class SV 
	{
		function SV($ten,$mssv)
		{
			$this->Ten = $ten;
			$this->MSSV= $mssv;
		}
		
	};
	
	$mangSV = array();
	
	
	array_push($mangSV,new SV("Quan",345));
	array_push($mangSV,new SV("XXX",775));
	//array_push($mangSV,"TAQ","@@");

	echo json_encode($mangSV);
	
?>