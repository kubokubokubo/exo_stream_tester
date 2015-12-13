# exo_stream_tester
Test a DASH stream url with ExoPlayer Demo 

clone this project:
git clone https://github.com/kubokubokubo/exo_stream_tester.git

clone ExoPlayer repository in the right place
cd StreamTester
git clone https://github.com/google/ExoPlayer.git

in the ExoPlayer/demo/build.gradle, change the lines
dependencies {
    compile project(':library')
}
to 
dependencies {
    compile project(':ExoPlayer:library')
}

open in Android Studio, clean project

To change the version of exoplayer: in exowrapper build.gradle, set the exoPlayerVersionHash value
to the commit hash from ExoPlayer master branch that you want to use and clean