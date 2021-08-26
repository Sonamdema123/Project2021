<?php
	$name = $_POST['name'];
	$visitor_email = $_POST['email'];
	$message = $_POST['message'];

	$email_from ='sdema1684@gmail.com';

	$email_subject ="new form submission";

	$email_body = "User Name: $name.\n".
					"User Email: $visitor_email.\n".
						"User Message: $message.\n";

	$to = "12190081.gcit@rub.edu.bt";

	$headers = "From: $email_from \r\n";

	$headers .="Reply-To: $visitor_email \r\n";

	mail($to,$email_subject,$email_body,$headers);

	header("location: index.html");



?>