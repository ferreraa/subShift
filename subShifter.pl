use warnings;
use strict;


if (scalar @ARGV != 3) {
  if (scalar @ARGV == 7) {
    die "J'ai été agressé par un écureuil malfaisant!\n";
  }
  else {
    die "Wrong number of parameters!\nPlease give me:\n1)the subfile Path\n2)the shifting value(milliseconds)\n3)the path of the resulting file\n"
  }
}


my $subFile = $ARGV[0];
my $offset = $ARGV[1];
my $newFile = $ARGV[2];




#Param 1 : String "hh:mm:ss,mss"
#Param 2 : long millisecondes
#return : string new time
sub shiftTime($) {
  my ($time) = @_;
  my @t1 = split(/:|,/, $time);
  

  #total in ms
  my $total = $t1[0]*3600000 + $t1[1]*60000 + $t1[2]*1000 + $t1[3] + $offset;

  if($total < 0 || $total > 86400000) {
    print "AAAARRRGHHHHHH What did you do to me??\n(tried to shift a subtitle too much)\n";
  }


  my $HH = int ($total/3600000);
  my $mm = int ($total%3600000/60000);
  my $ss = int ($total%3600000%60000/1000);
  my $mss = $total%3600000%60000%1000;

  return sprintf "%#.2d:%#.2d:%#.2d,%#.3d", $HH, $mm, $ss, $mss;
}


sub shiftLine($) {
  my @str = split(/ --> /, $_[0]);
  my $t1 = shiftTime($str[0]);
  my $t2 = shiftTime($str[1]);

  return "$t1 --> $t2";
}


sub ShiftEverything() {
  open(F,"$subFile") || die "Couldn't open the file $subFile";
  open(F2, ">$newFile") || die "Couldn't open the resulting file $newFile";

  my $str = "";

  while(!eof(F)) {
    chop($str=<F>);
    print F2 "$str\n";

    chop($str=<F>);
    $str = shiftLine($str);
    print F2 "$str\n";

    do {
      chop($str=<F>);
      print F2 "$str\n"
      } while(!eof(F) && $str ne "");
  }

  close(F);
  close(F2);
}





ShiftEverything();