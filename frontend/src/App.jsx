import {useState} from 'react'
import UseEarthquakes from './hooks/useEarthquakes'

function App() {
    const {
        earthquakes,
        loading,
        error,
        fetchAndStoreEarthquakes,
        filterByMagnitude,
        filterByTime,
        deleteById
    } = UseEarthquakes();

    const [minMag, setMinMag] = useState(2.5)

    const handleMagChange = (e) => {
        const val = e.target.value;
        setMinMag(val);
        filterByMagnitude(val);
    }

    const handleTimeChange = (e) => {
        const dateValue = e.target.value;
        if (dateValue) {
            const timestamp = new Date(dateValue).getTime();
            filterByTime(timestamp);
        }
    }

    const formatDate = (timestamp) => {
        if (!timestamp) return 'N/A'
        return new Date(timestamp).toLocaleString()
    }

    const getMagClass = (mag) => {
        if (!mag && mag !== 0) return 'bg-secondary'
        if (mag >= 7) return 'bg-danger'
        if (mag >= 5.5) return 'bg-danger bg-opacity-75'
        if (mag >= 4.5) return 'bg-warning text-dark'
        if (mag >= 3.5) return 'bg-secondary'
        return 'bg-secondary bg-opacity-50'
    }
    return (
        <div className="container py-5">
            <header className="text-center mb-5 position-relative">
                <h1 className="display-4 fw-bold">Earthquake Tracker</h1>
                <p className="lead text-muted">Monitor recent seismic activity around the world</p>
                <button
                    className="btn btn-primary shadow-sm position-absolute top-0 end-0 mt-3 d-flex align-items-center gap-2"
                    onClick={fetchAndStoreEarthquakes}
                    disabled={loading}
                >
                    {loading && (
                        <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                    )}
                    {loading ? 'Syncing...' : 'Sync Latest Data'}
                </button>
            </header>

            <section className="card shadow-sm mb-5">
                <div className="card-body">
                    <div className="row g-3">
                        <div className="col-md-6">
                            <label htmlFor="minMag" className="form-label fw-bold text-dark">Min
                                Magnitude: {minMag}</label>
                            <input
                                type="range"
                                className="form-range"
                                id="minMag"
                                min="0"
                                max="9"
                                step="0.1"
                                value={minMag}
                                onChange={handleMagChange}
                            />
                        </div>

                        <div className="col-md-6">
                            <label htmlFor="timeFilter" className="form-label fw-bold text-dark">Filter from
                                Date:</label>
                            <input
                                type="date"
                                className="form-control"
                                id="timeFilter"
                                onChange={handleTimeChange}
                            />
                        </div>
                    </div>
                </div>
            </section>

            <main>
                {loading && (
                    <div className="text-center py-5">
                        <div className="spinner-border text-primary" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </div>
                        <p className="mt-3">Processing earthquake data...</p>
                    </div>
                )}

                {error && (
                    <div className="alert alert-danger shadow-sm text-center" role="alert">
                        {error}
                    </div>
                )}

                {!loading && !error && (
                    <div className="row row-cols-1 row-cols-md-2 g-4">
                        {earthquakes && earthquakes.length > 0 ? (
                            earthquakes.map((eq) => {
                                const data = eq.properties || eq;
                                const mag = typeof data.magnitude === 'number' ? data.magnitude : data.magnitude;
                                return (
                                    <div key={eq.id} className="col">
                                        <div
                                            className="card h-100 shadow-sm border-0 position-relative overflow-hidden">
                                            <div className="card-body d-flex align-items-center gap-3">
                                                <div
                                                    className={`badge rounded-circle d-flex align-items-center justify-content-center ${getMagClass(mag)}`}
                                                    style={{width: '50px', height: '50px', fontSize: '1.2rem'}}
                                                >
                                                    {typeof mag === 'number' ? mag.toFixed(1) : 'N/A'}
                                                </div>
                                                <div className="flex-grow-1 overflow-hidden">
                                                    <h5 className="card-title text-truncate mb-1">{data.place || 'Unknown Location'}</h5>
                                                    <p className="card-subtitle text-muted small">{formatDate(data.time)}</p>
                                                    <div className="d-flex gap-2 mt-2">
                                                        {data.url && (
                                                            <a
                                                                href={data.url}
                                                                target="_blank"
                                                                rel="noopener noreferrer"
                                                                className="btn btn-sm btn-outline-primary"
                                                            >
                                                                View Details
                                                            </a>
                                                        )}
                                                        <button
                                                            className="btn btn-sm btn-outline-danger"
                                                            onClick={() => deleteById(eq.id)}
                                                        >
                                                            Delete
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })
                        ) : (
                            <div className="col-12 text-center py-5 text-muted">
                                <p className="fs-5">No earthquakes found. Try syncing or adjusting filters.</p>
                            </div>
                        )}
                    </div>
                )}
            </main>

        </div>
    )
}

export default App
