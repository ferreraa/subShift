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

my $tempFile;

#if the sourceFile and the destinationFile are the same, then we must use a temporary file.
#Another way would be to store averything in a string and then print all at once.
#Which is what I'm going to do. That's why this piece of code is commented.
=pod
if($subfile eq $newFile) {
  $tempFile = $newFile;
  $newFile = "jesuisunecureuilmalfaisantcommetouslesautresecureuilsjechercheadominerlaplaneteetsiquelquunessaiedemenempecherjeletue";
}
=cut

#Param 1 : String "hh:mm:ss,mss"
#return : string new time
sub shiftTime($) {
  my ($time) = @_;
  my @t1 = split(/:|,/, $time);
  

  #total in ms
  my $total = $t1[0]*3600000 + $t1[1]*60000 + $t1[2]*1000 + $t1[3] + $ARGV[1];

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

#returns true if the given line must be edited
sub isTimeLine($) {
  my @str = split(/ --> |,|:/, $_[0]);
  return (scalar @str == 8);
}

sub ShiftEverything() {
  open(F,"$subFile") || die "Couldn't open the file $subFile";
#  open(F2, ">$newFile") || die "Couldn't open the resulting file $newFile";

  my $line = "";
  my $res = "";

  while(!eof(F)) {

    chomp($line=<F>);

    if(isTimeLine($line)) {
      $line = shiftLine($line);
    }

    $res .="$line\n";
  }

  close(F);

  open(F2, ">$newFile") || die "Couldn't open the resulting file $newFile";

  print F2 $res;

  close(F2);
}


ShiftEverything();