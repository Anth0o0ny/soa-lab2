FROM consul:1.15.4
ADD ./config /consul/config/

ADD ./certs /certs/

ENTRYPOINT ["/bin/consul", "agent", "-server", "-bootstrap-expect=1", "-ui", "-data-dir=/consul/data", "-client=0.0.0.0"]