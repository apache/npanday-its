//
// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//
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