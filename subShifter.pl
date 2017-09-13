use warnings;
use strict;

my subfile = @ARGV[0];



sub ExtractionDesFichiers($$)
{
   my ($FileName,$Path)=@_; # Tableau des paramètres 
   open(F,$FileName) || die "Erreur d'ouverture du fichier $FileName\n";
   my $str="";
   my $str2="";
   my $Num=0;

   my $Go;

   open(COL,">$Path/$Collection") || die "Erreur de creation de Collection\n";
   while(!eof(F)){
     if($str =~m /\.I\s/){ # On regarde si s$tr contient la chaîne .I
        close(NF);
        $str =~s/\.I\s//g; # Dans $str, on supprime la chaîne .I avant le numéro de document
        $Num=$str;
        print COL "CACM-$Num\n";
        print "Processing ... CACM-$Num\n";
        open(NF,">$Path/CACM-$Num");
  open(NF2,">$Path/CACM-$Num.flt");
     }
     if(($str=~ m/\.T/) || ($str=~ m/\.A/) || ($str=~ m/\.W/) || ($str=~ m/\.B/)) { # Si $str contient une des balises que l'on veut 
        $Go=1;
        while($Go==1){  # Tant que l'on ne rencontre pas une nouvelle balise
           chop($str=<F>);
           if(($str eq "\.W") || ($str eq "\.B") || ($str eq "\.N") || ($str eq "\.A") || ($str eq "\.X") || ($str eq "\.K") || ($str eq "\.T") || ($str eq "\.I")){
             $Go=0;
#             break;
           }
           else{
       $str2=lc($str);
       $str2=~s/(\,|\=|\/|\.|\?|\'|\(|\)|\_|\$|\%|\+|\[|\]|\{|\}|\&|\;|\:|\~|\!|\"|\@|\#|\^|\*|\||\<|\>|\-|\\s|\\)//g;  #J'ai rajouté le "
             print NF "$str "; # On écrit le contenu dans le fichier CACM-XX
       print NF2 "$str2 ";
           }
        }
     }
     else{
       chop($str=<F>);
     }
  }
  close(F);
  close(NF2);
  close(NF);
  close(COL);
}




sub CreerStopList($)
{
  my ($Common)=@_;
  my $str="";
  open(F,$Common) || die "Erreur CreerStopList\n";
  while(!eof(F)){
    chop($str=<F>);
    $stopList{$str} = 1;
  }

  close(F);
}



sub Filtre($)
{
  my ($FileName)=@_;
  open(F,"$FileName.flt") || die "Erreur Filtre\n";
  open(NF,">$FileName.stp");
  my $str="";
  my @line;
  while(!eof(F)){
    chop($str=<F>);
    @line=split " ", $str;
    foreach my $word(@line){
      if(!exists $stopList{$word}){
        print NF "$word ";
      }
    }
  }

  close(F);
  close(NF);
}



sub Filtres($)
{
  my ($Path)=@_;

  my $FileName="";
  open(COL,"$Path/$Collection") || die "Erreur Filtres\n";
  while(!eof(COL)){
    chop($FileName=<COL>);
    Filtre("$Path/$FileName");
  }

  close(COL);

}
