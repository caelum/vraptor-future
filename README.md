Testes:

5 threads, 200 requisições
media / warmup / real1 / real2 (ms) / media
antigo: 588.789 / 533.152 / 545.907
	583/
novo com early e execute: 537.641 / 201,40.279 / 180
novo com early e jsp paralelo: 637.863 / 527.763 / 525.865 / 526.814
novo com early e segura pra comecar jsp: 
novo com early e jsp paralelo e buffer: 
novo 2 com recycle session entre um unico user: 