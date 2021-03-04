# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# Uncomment the following line if you don't like systemctl's auto-paging feature:
# export SYSTEMD_PAGER=

# User specific aliases and functions
alias oggsci12f11='. /opt/oracle/env_12c_f11.env ; rlwrap /oramnt/oracle/ogg/12.2.0.2_f11/ggsci'
alias oggsci18f11='. /opt/oracle/env_18c_f11.env ; rlwrap /oramnt/oracle/ogg/18.1.0.0_f11/ggsci'
alias oggsci18f18='. /opt/oracle/env_18c_f18.env ; rlwrap /oramnt/oracle/ogg/18.1.0.0_f18/ggsci'
alias glog18='less /oramnt/oracle/ogg/18.1.0.0_f18/ggserr.log'
alias oggsci19f19='. /opt/oracle/env_19c_f19.env ; rlwrap -pYellow /oramnt/oracle/ogg/19.1.0.0_f19/ggsci'
alias oggsci18f12='. /opt/oracle/env_18c_f12.env ; rlwrap -pYellow /oramnt/oracle/ogg/18.1.0.0_f12/ggsci'
#alias oggsci19BigData='. /opt/oracle/env_19c_f19.env ; . /opt/oracle/env_jre8.env ; rlwrap -pYellow /oramnt/oracle/ogg/19.1.0.0.5_BigData/ggsci'
alias oggsci19BigData='. /opt/oracle/X_dbsetenv ; rlwrap -pYellow /oramnt/oracle/ogg/19.1.0.0.5_BigData/ggsci'
shopt -s direxpand

export JAVA_HOME=/oramnt/oracle/jre/jre1.8.0_271
#export JAVA_HOME=/oramnt/oracle/jdk/jdk1.8.0_271
#export JAVA_HOME=/oramnt/oracle/jdk/jdk-15.0.1
export PATH=$JAVA_HOME/bin:$PATH
export LD_LIBRARY_PATH=$JAVA_HOME/lib:$JAVA_HOME/lib/amd64:$JAVA_HOME/lib/amd64/server
#export LD_LIBRARY_PATH=$JAVA_HOME/lib:$JAVA_HOME/lib/server
