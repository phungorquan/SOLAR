var d=new Date();
d.setTime( d.getTime() + d.getTimezoneOffset()*60*1000 + 7*3600*1000 );
var dformat = [d.getFullYear(),
               d.getMonth()+1,
               d.getDate()].join('-')+' '+
              [d.getHours(),
               d.getMinutes(),
               d.getSeconds()].join(':');
console.log(dformat);