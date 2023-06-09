# HTTP Status�� Response

Backend Developer�� ǥ�� ������ �����ؼ� ������ �� �־�� �մϴ�.

���� ���� ������ �Ǿ� ���� ���� ����� case
- ���� ���� ��) status code 200 / id ����
- ���� ���� ��) status code 500 / traceback: servletException~ (�� ����)

Ŭ���̾�Ʈ�� �ش� ������ ���� ��� �Ǵ��� �� ������?
-> �Է��� URI, request, response�� �����ϴ� ������ ���ϴ� ������ �ʿ� (��ȿ����)

�̷��� ������ٴ� ��û���� �ش��ϴ� �����ڵ带 ������ ǥ�� ���� ������ �����ϴ� ���� �ٶ����մϴ�

## HTTP Status code
- Status code�� ù ��° ���ڿ� ���� response class�� �޶����ϴ�

### 1XX
Informational responses : ��û�� �޾����� ���μ����� �����

|Status code|Status text|Description|
|:-------------:|:-------------:|:-------------:|
|100|Continue|��û�� ���� �κ��� �޾Ƶ鿩������ Ŭ���̾�Ʈ�� ��� �̾ ������ ��, �̹� ��û�� �Ϸ��� ��쿡�� �����ص� ��|
|101|witching Protocol|��û ����� Update �ʵ� �� �ϳ��� ������ ���������� ������|
|102|Processing|������ ��û�� �����ϰ� �̸� ó���ϰ� ������ ����� �� ������ �˷��� �� ����|

### 2XX
Success : ��û�� ���������� �޾����� �ν��߰� ��������

|Status code|Status text|Description|
|:-------------:|:-------------:|:-------------:|
|200|OK|��û�� ���������� ó����|
|201|Created|���������� ������ ���� ��û�� �޾����� ������ �� ���ҽ��� �ۼ��� (�밳 POST, PUT�� ��)|
|202|Accepted|��û�� ���������� ���� ó������ ���� (��û�� ��������)|
|203|Non-Authoritative Information|��û�� ���������� ó�������� �ٸ� �ҽ����� ���ŵ� ������ ������, ������ ���� ���� ����|
|204|No Content|������ ��û�� ���������� ó�������� ������ �������� ����|
|205|Reset Content|������ ��û�� ���������� ó�������� ���ο� ������ Ȯ���ؾ� ���� �˷��� (���ΰ�ħ ���� �̿�)|
|206|Partial Content|������ GET ��û�� �Ϻθ� ���������� ó���� - Content-Range�� Date ����� �ݵ�� ����|
|207|Multi Status|���� ���� ���ҽ��� ���� status code�� ���� �ִ� ��Ȳ���� ������ ���� ����|
|208|Already Reported|DAV���� ���|

### 3XX
Redirection : Ŭ���̾�Ʈ�� ��û�� ���� ������ ��ġ�� �����ϰų� ����� ������ ����

|Status code|Status text|Description|
|:-------------:|:-------------:|:-------------:|
|300|Multiple Choice|Ŭ���̾�Ʈ�� ���ÿ� ���� ������ ������ ��û�� ������ ��� Ŭ���̾�Ʈ�� �������� ��ȯ|
|301|Moved Permanently|��û�� ���ҽ��� URI�� ����� -> ����� URI�� ���� ������ �Բ� ����|
|302|Found|��û�� ���ҽ��� URI�� �Ͻ������� ����� ���̹Ƿ� ���� ��û�ߴ� URI�� ��û�ؾ� ��|
|303|See Other|Ŭ���̾�Ʈ�� ��û�� �۾��� �ϱ� ���ؼ��� �ٸ� URI���� ���� �� �� Ŭ���̾�Ʈ���� ��|
|304|Not Modified|������ ��û�� ���Ͽ� �޶��� ���� ���� (ĳ�ø� �������� ����)|
|305|Use Proxy|proxy�� ���� ��û�Ǿ�� ��|
|306|Unused|������ ������� �ʴ� �ڵ� -> ���� ����� ���� ����Ǿ� ����|
|307|Temporary Redirect|302�� �����ϳ� Ŭ���̾�Ʈ�� ���� HTTP �޼ҵ嵵 �����ϸ� �ȵ�|
|308|Permanent Redirect|��û�� ���ҽ��� ���������� �ٸ� URI�� ��ġ�ϰ� ����, 301�� �����ϳ� HTTP �޼ҵ嵵 �������� �� ��|

### 4XX
Client Error : Ŭ���̾�Ʈ�� �߸��� ��û

|Status code|Status text|Description|
|:-------------:|:-------------:|:-------------:|
|400|Bad Request|�߸��� �������� ��û�� ������ �־� ������ ������ �� ����|
|401|Unauthorized|��û�� ���� ���� ������ �ʿ��� (ex. ��ū�� ����)|
|402|Payment Required|���� �ý����� ���� ����������� ����� ������� ����|
|403|Forbidden|Ŭ���̾�Ʈ�� ��û�� �������� ���� ������ �Ǹ��� ���� (�ſ� ������ �Ǿ����� ������ ����)|
|404|Not Found|��û�� URI�� ã�� �� ����|
|405|Method Not Allowed|Ŭ���̾�Ʈ�� ���� �޼ҵ尡 �ش� URI���� �������� ����|
|406|Not Acceptable|Ŭ���̾�Ʈ�� ��û�� ���� �����Ҹ��� �������� ����|
|407|Proxy Authentication Required|401�� �����ϳ�, proxy�� ���� �����ؾ� ��|
|408|Request Timeout|��û�� �����ϴ� �ð��� ���� �ɷ� ��û�� ���� (������ �ʰ� ���� ���� ����)|
|409|Conflict|Ŭ���̾�Ʈ�� ��û�� ������ ���¿� �浹�� �߻��� �� ����|
|410|Gone|��û�� URI�� �� �̻� ������ �ʰ� �������|
|411|Length Required|��û ����� Content-length�� ���ԵǾ�� ��|
|412|Precondition Failed|��û ����� ������ ������ ���ǿ� �������� ����|
|413|Payload Too Large|request payload�� �������� ������ �ִ� ũ�⺸�� ŭ|
|414|URI Too Long|��û�� URI�� �ʹ� �� ó���� �� ����|
|415|Unsupported Media Type|������ �������� �ʴ� �̵�� ������ ��û��|
|416|Requested Range Not Satisfiable|��û ����� �ִ� Range �ʵ尡 �߸���|
|417|Expectation Failed|��û ����� �ִ� Expect �ʵ尡 �������� ����|
|418|I'm a teapot|������ Ƽ���̹Ƿ� Ŀ�� �����⸦ ��������|
|421|Misdirected Request|��û�� ������ ������ �� ���� ������ ������|
|422|Unprocessable Entity|���� ������ ���Ͽ� ó���� �� ����?|
|423|Locked|��û�� ���ҽ��� �����ϴ� ���� �������|
|424|Failed Dependency|���� ��û�� �����߱� ������ ������ ��û�� ��������|
|425|Too Early|������ ����� �� �ִ� ��û�� ó���ϴ� ���� ��ġ ����|
|426|Upgrade Required|���� ��û�� �������ݿ� ���� ó���� ������, �ٸ� �������ݷ� ���׷��̵带 �ϸ� ������ ó������ ���� ����|
|428|Precondition Required|�ʼ� ���� ���� ����� ������|
|429|Too Many Requests|Ŭ���̾�Ʈ�� ������ �ð��� �ʹ� ���� ��û�� ����|
|431|Request Header Fields Too Large|��û�� ��� �ʵ尡 �ʹ� Ŀ�� ó���� �� ����, ��� �ʵ带 �ٿ��� �ٽ� ��û�ؾ� ��|
|451|Unavailable For Legal Reasons|Ŭ���̾�Ʈ�� ��û�� ���� ���ο� ���� �˿��� �ҹ����� ���ҽ���|

### 5XX
Server Error : �������� Ŭ���̾�Ʈ�� ��û�� ���� ������ ������ ������ �� ����

|Status code|Status text|Description|
|:-------------:|:-------------:|:-------------:|
|500|Internal Server Error|������ ������ ������ �� ����|
|501|Not Implemented|������ �������� �ʴ� ���ο� �޼ҵ带 ����Ͽ� ��û��, Ŭ���̾�Ʈ ��û�� ���� ������ ������ �� �ִ� ����� ����|
|502|Bad Gateway|���� ���� �������� ������ �߻�, proxy�� gateway ��� ������|
|503|Service Unavailable|���� ������ �Ͻ������� ����� �Ұ���, �Ϲ������� ���������� ���� �ߴܵǰų� �����ϰ� �ɸ� ������|
|504|Gateway Timeout|������ �ٸ� ������ ��û�� �������� delay�� �߻��Ͽ� ó���� �Ұ�����|
|505|HTTP Version Not Supported|������ �������� �ʰų� �������� ���� �������ݷ� ��û�� ��|
|506|Variant Also Negotiates|������ ���� ���� ������ ����|
|507|Insufficient Storage|������ ���� ���� ������ ����|
|508|Loop Detected|��û�� ó���ϴ� ���� ���� ������ ������|
|510|Not Extended|������ ó���ϱ� ���ؼ��� ��û�� �� Ȯ���ؾ� ��|
|511|Network Authentication Required|Ŭ���̾�Ʈ�� ��Ʈ��ũ �׼����� �������� ������ �ʿ���|

## �츮�� �˾ƾ� �ϴ� Status code��?
- 2xx
	- 200 OK : ��κ��� ������ ���������� ����Ǵ� ���
	- 201 CREATED : POST, PUT method ���� ��쿡�� ���� ��
	- 204 No Content : DELETE method�� ������ ����� ����ϱ⵵ ��

- 4xx
	- 400 Bad Request : �߸��� ��û�� ���� ���
	- 401 Unauthorized : �α��� ���� ������ ���
	- 403 Forbidden : ������ ���� ���
	- 404 Not Found : �ش� ��Ұ� �������� �ʴ� ���
	- 405 Method Not Allowed : �ش� URI���� (GET, POST �� ����ڰ� ��û��) ��û�� ó���� �� ���� ���
	- 409 Conflict : ȸ�� ���� ��� �ߺ��� ���� �Է��Ͽ� �浹�� �Ͼ �� �ִ� ���

- 5xx
	- 500 Internal Server Error : ���� ���� ������ ���

�� ���丶�� ��캰�� ���� �ڵ�� ������ �����ϱ� ������, �� �ڼ��� �˸� �� ���� Ŭ���̾�Ʈ���� �� ��Ȯ�� ������ ���� �� �ֽ��ϴ�!

�ش� �����ڵ忡 ���� �����ϸ鼭 Ŭ���̾�Ʈ�� ���� ǥ�� ������ �����Ͽ� �Ѱ��ִ� ������ ���� ������ �Ͻñ� �ٶ��ϴ�!