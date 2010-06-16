package com.aworldforus
{
	import mx.utils.ArrayUtil;
	
	public class ArrayUtils
	{
		public static function difference( array1: Array, array2: Array ) : Array
		{		  				
			var eltOnlyInFirst : Array = new Array();
	        var eltOnlyInSecond: Array = new Array();
	        
	        if( array2 == null )
	        {
                if( array1 != null )
                    eltOnlyInFirst = array1;
	        }
	        else if( array1 == null )
	        {
	           eltOnlyInSecond = array2;
	        } 
	        else
	        {
	            for each( var elt1: Object in array1 )
	            {
	                if( ArrayUtil.getItemIndex( elt1, array2 ) == -1 )
	                    eltOnlyInFirst.push( elt1 );
	            }
	            for each( var elt2: Object in array2 )
	            {
	                if( ArrayUtil.getItemIndex( elt2, array1 ) == -1 )
	                    eltOnlyInSecond.push( elt2 );                             
	            }
	        }
	        return new Array( eltOnlyInFirst, eltOnlyInSecond );
	    }        
	}
}