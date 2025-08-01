import http from 'k6/http';
import { sleep } from 'k6';

// 부하 설정
export const options = {
    vus: 100,
    duration: '10m',
};

// 캐시 hit 계좌(자주 조회)
const HIT_ACCOUNTS = ['1', '2', '3', '4', '5', '6', '7', '8' ];

// 캐시 miss 계좌(덜 자주 조회, 하지만 반드시 존재하는 계좌)
const MISS_ACCOUNTS = ['9', '10', '11', '12'];

export default function () {
    // 80% 확률로 캐시 hit 대상, 20% 확률로 캐시 miss 대상
    const isHit = Math.random() < 0.8;
    let accountId;

    if (isHit) {
        accountId = HIT_ACCOUNTS[Math.floor(Math.random() * HIT_ACCOUNTS.length)];
    } else {
        accountId = MISS_ACCOUNTS[Math.floor(Math.random() * MISS_ACCOUNTS.length)];
    }

    http.get(`http://192.168.1.132:8080/accounts/${accountId}/interest`);
    sleep(1);
}