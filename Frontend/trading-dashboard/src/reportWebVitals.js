import { getCLS, getFID, getFCP, getLCP, getTTFB } from 'web-vitals';

const reportWebVitals = (onPerfEntry) => {
    const send = (metric) => {
        const event = new CustomEvent("web-vital-report", {
            detail: JSON.stringify(metric),
        });
        window.dispatchEvent(event);
        if (onPerfEntry) onPerfEntry(metric);
    };

    getCLS(send);
    getFID(send);
    getFCP(send);
    getLCP(send);
    getTTFB(send);
  };

  export default reportWebVitals;