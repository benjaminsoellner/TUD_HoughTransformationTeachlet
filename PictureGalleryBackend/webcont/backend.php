<?php 
	require('config.inc.php');
	
	define('STATUS_UPLOAD',   0);
	define('STATUS_UPLOADED', 1);
	
	function createScaledImage($source, $destination, $maxWidth, $maxHeight) {
		@list($width, $height, $type) = getimagesize($source);
		switch ($type) {
			case 1:  $image = imagecreatefromgif($source);  break;
			case 2:  $image = imagecreatefromjpeg($source); break;
			case 3:  $image = imagecreatefrompng($source);  break;
			default: return false;
		}
		if (($width > $maxWidth) || ($height > $maxHeight)) {
			$oldWidth = $width; $oldHeight = $height;
			if ($width > $maxWidth) {
				$height = ceil( $height * $maxWidth/$width );
				$width  = $maxWidth;
			}
			if ($height > $maxHeight) {
				$width  = ceil( $width * $maxHeight/$height );
				$height = $maxHeight;
			}
			$newImage = imagecreatetruecolor($width, $height);
			imagecopyresampled($newImage, $image, 0,0 , 0,0 , $width,$height, $oldWidth,$oldHeight ); 
			imagedestroy($image);
		} else {
			$newImage = $image;
		}
		@umask(0000);
		switch ($type) {
			case 1:  imagegif($newImage, $destination);  break;
			case 2:  imagejpeg($newImage, $destination); break;
			case 3:  imagepng($newImage, $destination);  break;
			default: return false;
		}
		imagedestroy($newImage);
		return true;
	}

	// File uploaded
	if (@$_FILES['image']) {
		$status = constant('STATUS_UPLOADED');
		$errors = array();
					
    	// Read image data
		$name     = $_FILES['image']['name'];
		$source   = $_FILES['image']['tmp_name'];
		$size     = $_FILES['image']['size'];
		$password = $_POST['password'];

		// Security checks
		if ($size > $CONFIG['max_size_kb']*1024) 
			array_push($errors, 'Die Dateigröße darf '.$CONFIG['max_size_kb'].' kB nicht übersteigen.');
		if (($password != '') && (md5($password) != $CONFIG['staff_password']))
			array_push($errors, 'Das eingegebene Password ist falsch!');
		
		if ($errors == array()) {
			
			// Find destination folder
			if ($password != '') {
				$photoDestination = $CONFIG['staff_photos'] . ereg_replace('\s','',strtolower($name));
				$thumbDestination = $CONFIG['staff_thumbs'] . ereg_replace('\s','',strtolower($name));
			} else {
				$photoDestination = $CONFIG['student_photos'] . $name;
				$thumbDestination = $CONFIG['student_thumbs'] . $name;
			}
			
			// create scaled images
			$photoImage = createScaledImage($source, $photoDestination, $CONFIG['photos_width'], $CONFIG['photos_height']);
			$thumbImage = createScaledImage($source, $thumbDestination, $CONFIG['thumbs_width'], $CONFIG['thumbs_height']);
			
			if (($photoImage == false) || ($thumbImage == false)) 
				array_push($errors, 'Der Bildtyp der Datei konnte nicht erkannt werden (nur jpeg, gif und png sind erlaubt).');			
		} // end of if ($errors != ...) 
		@unlink($source);
		
	} else {
		$status = constant('STATUS_UPLOAD');
	} // end of if (@$_FILES ...)

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Bildgalerieverwaltung</title>

<style type="text/css">
<!--
	body { background: url(background.gif) white top left repeat-x fixed; }
	body, p, label, div, ul, h1, input { font-family: Verdana, Geneva, sans-serif; font-size: 11pt; }
	h1 { font-size: 14pt; color: #336; }
	form#upload label { width: 80px; margin-right: 10px; color: #336; font-weight: bold; display: inline; }
	form#upload input { width: 200px; background: url(graywhite.gif) white top left repeat-x; border: 1px solid #336; padding: 1px; margin: 1px; }
	form#upload input#submit { width: 100px; background-image: url(whitegray.gif); }
	div.error { color: #903; }
-->
</style>

</head>
<body>

<h1>Photogrammetrie Bildergalerie-Upload</h1>

<?php if ($status == constant('STATUS_UPLOAD')) { ?>
<p>Wählen Sie ein Bild von ihrer Festplatte aus, das Sie hochladen möchten.
Die Dateigröße darf <?php echo $CONFIG['restriction_size_kb']; ?> nicht überschreiten. 
Geben Sie zusätzlich ein Passwort ein, wenn Sie das Bild in den Mitarbeiterbereich hochladen möchten.</p>
<form action="#" enctype="multipart/form-data" method="post" id="upload">
	<label for="image">Bild:</label><input id="image" name="image" type="file" /><br />
    <label for="password">Password:</label><input id="password" name="password" type="password" /><br />
    <input type="submit" id="submit" value="Upload" />
</form>
<?php } else if ($status == constant('STATUS_UPLOADED') && $errors != array()) { ?>
<div class="errors">
  	Beim Hochladen der Datei traten Fehler auf. <a href="backend.php">Zurück.</a>
    <ul><?php foreach ($errors as $error) echo '<li>'.$error.'</li>'; ?></ul>
</div>
<?php } else if ($status == constant('STATUS_UPLOADED') && $errors == array()) { ?>
<div class="success">
   	Die Datei wurde erfolgreich hochgeladen. <a href="backend.php">Zurück.</a>
</div>
<?php } // end of if ($status == ...) ?>

</body>
</html>