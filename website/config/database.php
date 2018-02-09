<?php

return [

 

    'fetch' => PDO::FETCH_CLASS,
 

    'default' => env('DB_CONNECTION', 'mysql'),

 
    'connections' => [

        'sqlite' => [
            'driver' => 'sqlite',
            'database' => env('DB_DATABASE', database_path('database.sqlite')),
            'prefix' => '',
        ],

        'mysql' => [
            'driver' => 'mysql',
            'host' => env('DB_HOST', '167.205.7.226'),
            'port' => env('DB_PORT', '27107'),
            'database' => env('DB_DATABASE', 'pemilu'),
            'username' => env('DB_USERNAME', 'root'),
            'password' => env('DB_PASSWORD', ''),
            'charset' => 'utf8',
            'collation' => 'utf8_unicode_ci',
            'prefix' => '',
            'strict' => false,
            'engine' => null,
        ], 
    ],
 

    'migrations' => 'migrations',
  

];
