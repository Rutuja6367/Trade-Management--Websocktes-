// tradeService.js
const tradeService = {
    placeTrade: async (payload) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({
                    symbol: payload.symbol,
                    quantity: payload.quantity,
                    status: 'Executed',
                    executedAt: new Date().toISOString(),
                });
            }, 1000);
        });
    },
};

export default tradeService;
  