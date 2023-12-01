# mcsm

マイクラを低速な保存用ストレージと高速な実行用ストレージで同期をとりながら実行できるツール

| 環境変数           | オプション名         | ショートオプション | 説明                            | 初期値                    |
|----------------|----------------|-----------|-------------------------------|------------------------|
| SERVER_TYPE    | server-type    | s         | minecraft server type         | PAPER                  |
| SERVER_VERSION | server-version | v         | minecraft server version      | 1.19.4                 |
| BUILD_DIR      | build-dir      | b         | build server jar dir          | ./build                |
| SERVER_DIR     | server-dir     | d         | server dir                    | ./server               |
| RSYNC_RESOURCE | resource       | r         | rsync resource                | resource               |
| RSYNC_TARGET   | target         | t         | rsync target                  | target                 |
| RSYNC_PERIOD   | period         | p         | rsync period(s)               | 30                     |
| SOCKET         | socket         |           | process socket                | /var/lib/mcsm/run.sock |
| LOG_LEVEL      | log            | l         | log level \[WARN, INFO, FINE] | INFO                   |
|                |                |           |                               |                        |

