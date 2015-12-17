<?php
        header("Content-type: text/plain");
        require('config.inc.php');
        
        // get current directory
        $directory = substr( dirname($_SERVER['PHP_SELF']), strrpos($path,'/') ) . '/';
        if (@$_GET['resolution'] == 'photos') 
                $folders = array( $CONFIG['staff_photos'], $CONFIG['student_photos'] );
        else
                $folders = array( $CONFIG['staff_thumbs'], $CONFIG['student_thumbs'] );
        $authority = 'http://'.$_SERVER['SERVER_ADDR'].':'.$_SERVER['SERVER_PORT'];
        foreach ($folders as $folder)
                if ($handle = opendir($folder))
                        while (false !== ($file = readdir($handle))) 
                                if (ereg('^(.*)\.(jpg|jpeg|gif|png)$', $file))
                                        echo $authority . $directory . $folder . $file . "\r\n";
?>