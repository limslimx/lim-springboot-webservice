#!/usr/bin/env bash

# sciprt 디렉토리 내의 profile.sh를 import해오기 위한 설정
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy(){
    IDLE_PORT=$(find_idle_port)

    echo "> 전활할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    sudo service nginx reload
}