<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class WilayahModel extends Model
{
	protected $table      = 'wilayah';
    protected $primaryKey = 'wilayah_id';
    protected $guarded    = ['wilayah_id'];
		
    public function parent()
    {
        return $this->belongsTo('App\Models\WilayahModel','parent','wilayah_id');
    }

    public function child()
    {
        return $this->hasMany('App\Models\WilayahModel','parent','wilayah_id');
    }
	
}


